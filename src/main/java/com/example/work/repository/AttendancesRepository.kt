package com.example.work.repository

import com.example.work.entity.AttendanceEntity
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.time.Instant
import java.util.*

interface AttendancesRepository : CrudRepository<AttendanceEntity, Int> {

    @Query(
        """
            SELECT MAX(`user_attendances`.`registered_timestamp`) 
            FROM `user_attendances` WHERE `user_attendances`.`registered_by` = :lecturerIdRegisteredBy
        """
    )
    fun findMaxByRegisteredTimestampAndRegisteredBy(
        lecturerIdRegisteredBy: Int,
    ): Instant?

    @Query(
        """
            SELECT COUNT(`id`) 
            FROM `user_attendances` 
            WHERE `user_attendances`.`user_id` = :studentId
            AND `user_attendances`.`course_id` = :courseId
        """
    )
    fun findNumberOfAttendances(
        studentId: Int,
        courseId: Int,
    ): Int

    @Query(
        """
            SELECT `max_attendances`.`value`
            FROM `max_attendances` 
            WHERE `max_attendances`.`student_group_id` = :studentGroupId
            AND `max_attendances`.`course_id` = :courseId
        """
    )
    fun findMaxAttendances(
        studentGroupId: Int,
        courseId: Int,
    ): Int?
}
