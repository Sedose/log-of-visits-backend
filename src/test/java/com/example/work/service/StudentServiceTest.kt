package com.example.work.service

import com.example.work.controller.request.body.Attendance
import com.example.work.controller.request.body.AttendancesRequestBody
import com.example.work.model.Permission
import com.example.work.model.UserRole
import com.example.work.repository.AttendancesRepository
import com.example.work.repository.UserRepository
import com.example.work.repository.UserSettingsRepository
import com.example.work.security.SecurityUser
import io.kotest.core.spec.style.StringSpec
import io.mockk.mockk
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

class StudentServiceTest : StringSpec() {

    init {
        val studentAttendancesRepository = mockk<AttendancesRepository>()
        val userSettingsRepository = mockk<UserSettingsRepository>(
            relaxUnitFun = true
        )
        val userRepository = mockk<UserRepository>()
        val target = StudentService(
            studentAttendancesRepository,
            userSettingsRepository,
            userRepository,
        )

        "test registerAttendanceUsingFile()" {
            //given
            val lecturerIdRegisteredBy = 1


            //when
            val actual = target.registerAttendanceUsingFile(
                attendancesRequestBodyTestData,
                lecturerIdRegisteredBy
            )

            //then
//        val expected = UserSettingsResponseBody(userSettingsResponseBodiesTestData)

//        actual shouldBeEqualToComparingFields expected
        }
    }


    val attendancesRequestBodyTestData = AttendancesRequestBody(
        attendances = listOf(
            Attendance(
                fullName = "Name11 Name12 Name13",
                userAction = "userAction1",
                timestamp = "18.11.2020, 14:01:15"
            ),
            Attendance(
                fullName = "Name21 Name22 Name23",
                userAction = "userAction2",
                timestamp = "18.11.2020, 14:03:40"
            ),
            Attendance(
                fullName = "Name31 Name32 Name33",
                userAction = "userAction3",
                timestamp = "18.11.2020, 14:02:41"
            ),
        ),
        courseId = 1,
        registeredTimestamp = Date(1622177300)
    )


    val securityUserTestData = SecurityUser(
        "username",
        UserRole.LECTURER,
        listOf(SimpleGrantedAuthority(Permission.USER_SETTINGS_UPDATE.name)),
        1,
        true,
    )
}
