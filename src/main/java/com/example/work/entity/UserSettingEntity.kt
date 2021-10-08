package com.example.work.entity

import lombok.Value
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Value
@Table("users_settings")
class UserSettingEntity (
    @Id
    val id: Int,
    val userId: Int,
    val settingId: Int,
    val value: String,
)
