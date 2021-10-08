package com.example.work.model

enum class Permission(val permission: String) {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write"),

    COURSES_READ("courses:read"),
    USER_SETTINGS_UPDATE("user-settings:update"),
    ATTENDANCES_WRITE("attendances:write"),
    ATTENDANCES_REPORT_READ("attendances-report:read"),
    STUDENT_GROUPS_READ("student-groups:read"),
    USER_DETAILS_READ("user-details:read"),
    USER_SETTINGS_READ("user-settings:read"),
    USER_SETTINGS_WRITE("user-settings:write"),
}
