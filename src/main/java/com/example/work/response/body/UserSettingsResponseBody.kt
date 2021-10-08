package com.example.work.response.body

class UserSettingsResponseBody (
    val userSettings: List<UserSettingResponseBodyPart>
)

class UserSettingResponseBodyPart(
    val code: String,
    val description: String,
    val value: String,
    val defaultValue: String,
)
