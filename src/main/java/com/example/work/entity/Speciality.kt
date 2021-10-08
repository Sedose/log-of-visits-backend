package com.example.work.entity

import lombok.AccessLevel
import lombok.Getter
import lombok.experimental.FieldDefaults
import org.springframework.data.relational.core.mapping.Table

@Table("specialities")
@FieldDefaults(level = AccessLevel.PRIVATE)
class Speciality
