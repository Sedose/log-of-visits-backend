package com.example.work.controller

import com.example.work.response.body.UserDetails
import com.example.work.security.SecurityUser
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
open class UserDetailsController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user-details")
    open fun retrieveUserDetailsByToken(authentication: Authentication): UserDetails {
        val securityUser = (authentication.principal as SecurityUser)
        return UserDetails(securityUser.userRole.name)
    }
}
