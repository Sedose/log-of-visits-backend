package com.example.work.entity

import lombok.AccessLevel
import lombok.Getter
import lombok.experimental.FieldDefaults
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("courses")
@FieldDefaults(level = AccessLevel.PRIVATE)
class CourseEntity (
    @Id
    val id: Int,
    val name: String,
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val lecturerId: Int,
)
