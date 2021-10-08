package com.example.work.repository

import com.example.work.entity.Speciality
import org.springframework.data.repository.CrudRepository

interface SpecialitiesRepository : CrudRepository<Speciality, Int> {
}