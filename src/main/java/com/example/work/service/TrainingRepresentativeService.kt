package com.example.work.service

import com.example.work.entity.StudentGroupEntity
import com.example.work.mapper.CommonMapper
import com.example.work.repository.AttendancesRepository
import com.example.work.repository.SpecialitiesRepository
import com.example.work.repository.StudentGroupsRepository
import com.example.work.repository.UserRepository
import com.example.work.response.body.*
import org.springframework.stereotype.Service

@Service
open class TrainingRepresentativeService(
    private val studentGroupsRepository: StudentGroupsRepository,
    private val attendancesRepository: AttendancesRepository,
    private val specialitiesRepository: SpecialitiesRepository,
    private val userRepository: UserRepository,
    private val commonMapper: CommonMapper,
) {

    fun findAllStudentGroups(): List<StudentGroupEntity> =
        studentGroupsRepository.findAll()
            .sortedBy {
                it.name
            }

    fun formStudentAttendancesReportByGroupIdAndCourseId(
        studentGroupId: Int,
        courseId: Int,
        lecturerIdRegisteredBy: Long,
    ): StudentAttendancesReport {
        val studentsByGroup =
            studentGroupsRepository.findGroupStudents(studentGroupId)
        val maxAttendances = attendancesRepository.findMaxAttendances(
            studentGroupId,
            courseId,
        )
        return if (maxAttendances == null) {
            StudentAttendancesReportEmpty
        } else {
            StudentAttendancesReportFull(
                studentsByGroup.map {
                    ReportItem(
                        it.email,
                        it.firstName,
                        it.lastName,
                        it.middleName,
                        findAttendancesPercent(
                            it.id,
                            courseId,
                            maxAttendances,
                        )
                    )
                },
                findRegisteredByLecturer(lecturerIdRegisteredBy)
            )
        }
    }

    private fun findRegisteredByLecturer(lecturerIdRegisteredBy: Long): LecturerRegisteredBy {
        return commonMapper.map(
            userRepository.findById(lecturerIdRegisteredBy).orElseThrow()
        )
    }

    private fun findAttendancesPercent(
        studentId: Int,
        courseId: Int,
        maxAttendances: Int,
    ): Int = (
            attendancesRepository.findNumberOfAttendances(
                studentId,
                courseId,
            ) / maxAttendances.toDouble() * 100
    ).toInt()

    fun findStudentGroupById(groupId: Int): StudentGroupEntity?
        = studentGroupsRepository.findById(groupId).orElse(null)

    fun findAllSpecialities(): Any {
        TODO("Not yet implemented")
    }
}
