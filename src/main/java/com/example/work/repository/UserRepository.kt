package com.example.work.repository

import com.example.work.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<UserEntity, Long> {

    fun findByEmail(email: String): Optional<UserEntity>

    fun findByFirstNameAndMiddleNameAndLastName(
        firstName: String?,
        middleName: String?,
        lastName: String?,
    ): Optional<UserEntity>
}