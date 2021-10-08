package com.example.work.service

import com.example.work.controller.request.body.Attendance
import com.example.work.controller.request.body.AttendancesRequestBody
import com.example.work.repository.CoursesRepo
import com.example.work.mapper.CommonMapper
import com.example.work.entity.AttendanceEntity
import com.example.work.entity.UserSettingCode
import com.example.work.exception.ErrorCode
import com.example.work.exception.GeneralException
import com.example.work.repository.AttendancesRepository
import com.example.work.repository.UserRepository
import com.example.work.repository.UserSettingsRepository
import com.example.work.response.body.Course
import lombok.AllArgsConstructor
import lombok.ToString
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.*

@Service
open class LecturerService(
    private val coursesRepo: CoursesRepo,
    private val commonMapper: CommonMapper,
    private val attendancesRepository: AttendancesRepository,
    private val userSettingsRepository: UserSettingsRepository,
    private val userRepository: UserRepository,
) {

    fun retrieveAllCoursesByLecturerId(id: Int): List<Course> = commonMapper.map(
        coursesRepo.findAllByLecturerId(id)
    )

    fun registerAttendanceUsingFile(
        attendancesRequestBody: AttendancesRequestBody,
        lecturerIdRegisteredBy: Int
    ) {
        val now = attendancesRequestBody.registeredTimestamp.toInstant()
        val was = attendancesRepository.findMaxByRegisteredTimestampAndRegisteredBy(lecturerIdRegisteredBy)
        val userSettingMinFileUploadPeriodValueInSeconds = Duration.ofSeconds(
            userSettingsRepository.findByCode(
                UserSettingCode.MIN_STUDENT_ATTENDANCE_FILE_UPLOAD_INTERVAL.name
            ).value.toLong()
        )
        if (was != null && Duration.between(was, now) < userSettingMinFileUploadPeriodValueInSeconds) {
            throw GeneralException(ErrorCode.TOO_FREQUENT_FILE_UPLOADS)
        }
        attendancesRepository.saveAll(
            toAttendanceEntities(
                findDistinctAttendances(attendancesRequestBody),
                attendancesRequestBody.courseId,
                attendancesRequestBody.registeredTimestamp,
                lecturerIdRegisteredBy
            )
        )
    }

    fun findCourseById(courseId: Int): Course? {
        val course = coursesRepo.findById(courseId)
        return if (course.isPresent) {
            commonMapper.map(course.get())
        } else {
            null
        }
    }

    private fun findDistinctAttendances(
        attendancesRequestBody: AttendancesRequestBody,
    ) = attendancesRequestBody.attendances.asSequence()
        .filterNot {
            it.fullName.isNullOrBlank()
        }.groupBy {
            it.fullName
        }.filterNot {
            it.value.last().userAction == "Ушел"
        }.map {
            it.value.first()
        }

    private fun toAttendanceEntities(
        attendances: List<Attendance>,
        courseId: Int,
        registeredTimestamp: Date,
        lecturerId: Int,
    ): List<AttendanceEntity> {
        return attendances.map {
            toAttendanceEntity(
                it,
                courseId,
                registeredTimestamp,
                lecturerId,
            )
        }
    }

    @Throws(GeneralException::class)
    private fun toAttendanceEntity(
        attendance: Attendance,
        courseId: Int,
        registeredTimestamp: Date,
        lecturerId: Int,
    ) = AttendanceEntity(
        null,
        retrieveUserIdFromFullName(attendance.fullName!!),
        courseId,
        lecturerId,
        registeredTimestamp,
    )

    @Throws(GeneralException::class)
    private fun retrieveUserIdFromFullName(fullName: String): Int {
        val user = retrieveUser(fullName)
        return userRepository.findByFirstNameAndMiddleNameAndLastName(
            user.firstName, user.middleName, user.lastName
        ).orElseThrow { GeneralException(ErrorCode.CANNOT_GET_USER_BY_FULL_NAME) }.id
    }

    private fun retrieveUser(fullName: String): User {
        val splitFullName = fullName.split(" ").toTypedArray()
        if (splitFullName.size != 3) {
            throw GeneralException(ErrorCode.CANNOT_EXTRACT_PARTS_FROM_USER_FULL_NAME)
        }
        return User(
            toStringOrNull(splitFullName[0]),
            toStringOrNull(splitFullName[1]),
            toStringOrNull(splitFullName[2]),
        )
    }

    private fun toStringOrNull(partOfFullName: String) =
        partOfFullName.takeUnless { it == "_" }


    @ToString
    @AllArgsConstructor
    internal class User(
        val firstName: String?,
        val middleName: String?,
        val lastName: String?,
    )

    internal class DistinctAttendance(
        val fullName: String,
        val userActions: List<String>,
        val timestamp: String,
    )
}