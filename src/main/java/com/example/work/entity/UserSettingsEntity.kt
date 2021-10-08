package com.example.work.entity

import lombok.Value

@Value
class UserSettingsEntity (
    val code: String,
    val description: String,
    val value: String,
    val defaultValue: String,
)

enum class UserSettingCode {
    MIN_STUDENT_ATTENDANCE_FILE_UPLOAD_INTERVAL
}
