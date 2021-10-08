package com.example.work.repository

import com.example.work.entity.CourseEntity
import org.springframework.data.repository.CrudRepository

interface CoursesRepo : CrudRepository<CourseEntity, Int> {

    fun findAllByLecturerId(id: Int): List<CourseEntity>
}
