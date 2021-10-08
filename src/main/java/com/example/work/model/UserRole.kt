package com.example.work.model

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class UserRole(private val permissions: Set<Permission>) {
    TRAINING_REPRESENTATIVE(
        setOf(
            Permission.COURSES_READ,
            Permission.STUDENT_GROUPS_READ,
            Permission.USER_SETTINGS_UPDATE,
            Permission.USER_SETTINGS_READ,
            Permission.COURSES_READ,
            Permission.ATTENDANCES_REPORT_READ,
        ),
    ),
    LECTURER(
        setOf(
            Permission.USER_SETTINGS_UPDATE,
            Permission.USER_SETTINGS_READ,
            Permission.COURSES_READ,
            Permission.ATTENDANCES_WRITE
        )
    ),
    STUDENT(
        setOf(
            Permission.USER_SETTINGS_UPDATE,
            Permission.USER_SETTINGS_READ,
        )
    );

    val authorities: Set<SimpleGrantedAuthority>
        get() = permissions
            .map { SimpleGrantedAuthority(it.permission) }
            .toSet()
}
