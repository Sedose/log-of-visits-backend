package com.example.work.service

import org.springframework.beans.factory.annotation.Autowired
import com.example.work.mapper.CommonMapper
import com.example.work.repository.UserSettingsRepository
import com.example.work.response.body.UserSettingsResponseBody
import com.example.work.controller.request.body.UserSettingsRequestBody
import com.example.work.security.SecurityUser
import org.springframework.stereotype.Service

@Service("userService")
open class UserService(
    private val commonMapper: CommonMapper,
    private val userSettingsRepository: UserSettingsRepository,
) {

    fun findSettingsByUserId(userId: Int): UserSettingsResponseBody {
        return UserSettingsResponseBody(
            commonMapper.mapToUserSettingResponseBodyPartList(
                userSettingsRepository.findUserSettingsByUserId(userId)
            )
        )
    }

    fun updateUserSettings(
        userSettingsRequestBody: UserSettingsRequestBody,
        securityUser: SecurityUser
    ) {
        for (userSetting in userSettingsRequestBody.userSettings) {
            userSettingsRepository.updateUserSettingsByUserId(
                userSetting.code,
                userSetting.newValue,
                securityUser.id
            )
        }
    }
}