package com.example.work.controller.request.body

import lombok.AccessLevel
import lombok.Data
import lombok.NoArgsConstructor
import lombok.experimental.FieldDefaults
import javax.validation.constraints.NotNull

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Attendance (
    val fullName: String?,
    val userAction: String?,
    val timestamp: String?,
)
