package com.example.work.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
open class JwtConfigurator(
    private val userDetailsService: UserDetailsService,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Value("\${jwt.header}")
    private lateinit var authHeader: String

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.addFilterBefore(
            JwtTokenFilter(userDetailsService, authHeader),
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}