package com.example.work.entity

import lombok.AccessLevel
import lombok.experimental.FieldDefaults
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("student_groups")
@FieldDefaults(level = AccessLevel.PRIVATE)
class StudentGroupEntity (
    @Id
    val id: Int,
    val name: String,
)
