package com.example.work.entity

import com.example.work.model.Status
import com.example.work.model.UserRole
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
class UserEntity (
    @Id val id: Int,
    val email: String,
    val role: UserRole,
    val status: Status,
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
)
