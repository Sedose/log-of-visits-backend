package com.example.work.repository

import org.springframework.data.repository.CrudRepository
import com.example.work.entity.StudentGroupEntity
import com.example.work.entity.StudentEntity
import org.springframework.data.jdbc.repository.query.Query

interface StudentGroupsRepository : CrudRepository<StudentGroupEntity, Int> {

    @Query("""
        SELECT 
        users.id,
        users.email,
        users.first_name,
        users.middle_name,
        users.last_name
        FROM users INNER JOIN students
        ON users.id = students.id
        WHERE students.group_id = :studentGroupId
        """
    )
    fun findGroupStudents(
        studentGroupId: Int
    ): List<StudentEntity>
}
