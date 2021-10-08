package com.example.work.service

import com.example.work.controller.request.body.UserSettings
import com.example.work.controller.request.body.UserSettingsRequestBody
import com.example.work.response.body.UserSettingResponseBodyPart
import com.example.work.response.body.UserSettingsResponseBody
import com.example.work.entity.UserSettingsEntity
import com.example.work.mapper.CommonMapper
import com.example.work.model.Permission
import com.example.work.model.UserRole
import com.example.work.repository.UserSettingsRepository
import com.example.work.security.SecurityUser
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserServiceTest : StringSpec() {

    init {
        val commonMapper = mockk<CommonMapper>()
        val userSettingsRepository = mockk<UserSettingsRepository>(
            relaxUnitFun = true
        )
        val target = UserService(
            commonMapper,
            userSettingsRepository,
        )

        "test findSettingsByUserId()" {
            //given
            val userId = 1

            every {
                userSettingsRepository.findUserSettingsByUserId(userId)
            } returns userSettingsTestData

            every {
                commonMapper.mapToUserSettingResponseBodyPartList(userSettingsTestData)
            } returns userSettingsResponseBodiesTestData

            //when
            val actual = target.findSettingsByUserId(userId)

            //then
            val expected = UserSettingsResponseBody(userSettingsResponseBodiesTestData)

            actual shouldBeEqualToComparingFields expected
        }

        "test updateUserSettings()" {
            //when
            target.updateUserSettings(
                userSettingsRequestBodyTestData,
                securityUserTestData,
            )

            //then
            verify(
                exactly = userSettingsRequestBodyTestData.userSettings.size
            ) {
                userSettingsRepository.updateUserSettingsByUserId(
                    settingCode = any(),
                    settingValueNew = any(),
                    userId = any(),
                )
            }
        }
    }

    private val userSettingsTestData = listOf(
        UserSettingsEntity(
            "code1",
            "description1",
            "value1",
            "defaultValue1"
        ),
        UserSettingsEntity(
            "code2",
            "description2",
            "value2",
            "defaultValue2"
        ),
        UserSettingsEntity(
            "code3",
            "description3",
            "value3",
            "defaultValue3"
        ),
    )

    private val userSettingsResponseBodiesTestData =
        listOf(
            UserSettingResponseBodyPart(
                "code1",
                "description1",
                "value1",
                "defaultValue1"
            ),
            UserSettingResponseBodyPart(
                "code2",
                "description2",
                "value2",
                "defaultValue2"
            ),
            UserSettingResponseBodyPart(
                "code3",
                "description3",
                "value3",
                "defaultValue3"
            ),
        )

    private val userSettingsRequestBodyTestData =
        UserSettingsRequestBody(
            listOf(
                UserSettings(
                    "code1",
                    "newValue1"
                ),
                UserSettings(
                    "code2",
                    "newValue2"
                ),
                UserSettings(
                    "code3",
                    "newValue3"
                )
            ),
        )

    private val securityUserTestData = SecurityUser(
        "username",
        UserRole.LECTURER,
        listOf(SimpleGrantedAuthority(Permission.USER_SETTINGS_UPDATE.name)),
        1,
        true,
    )
}
