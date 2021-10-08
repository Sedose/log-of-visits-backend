package com.example.work.controller.request.body

class UserSettingsRequestBody(
    val userSettings: List<UserSettings>
)

class UserSettings(
    val code: String,
    val newValue: String,
)
