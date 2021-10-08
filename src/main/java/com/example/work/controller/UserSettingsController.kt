package com.example.work.controller

import com.example.work.controller.request.body.UserSettingsRequestBody
import com.example.work.response.body.UserSettingsResponseBody
import com.example.work.security.SecurityUser
import com.example.work.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user-settings")
open class UserSettingsController(
    private val userService: UserService
) {

    @GetMapping
    @PreAuthorize("hasAuthority('user-settings:read')")
    open fun fetchUserSettings(authentication: Authentication): UserSettingsResponseBody {
        val securityUser = authentication.principal as SecurityUser
        return userService.findSettingsByUserId(securityUser.id)
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user-settings:update')")
    open fun saveAllChanges(
        @RequestBody userSettingsRequestBody: UserSettingsRequestBody,
        authentication: Authentication
    ) {
        val securityUser = authentication.principal as SecurityUser
        userService.updateUserSettings(
            userSettingsRequestBody,
            securityUser,
        )
    }
}