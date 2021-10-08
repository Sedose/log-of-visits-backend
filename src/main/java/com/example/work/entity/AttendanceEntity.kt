package com.example.work.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * registeredBy - id of lecturer
 */
@Table("user_attendances")
class AttendanceEntity(
    @Id val id: Int?,
    val userId: Int,
    val courseId: Int,
    val registeredBy: Int,
    val registeredTimestamp: Date,
)
