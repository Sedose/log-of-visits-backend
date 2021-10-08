package com.example.work.controller.request.body

import lombok.Data
import lombok.NoArgsConstructor
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Data
@NoArgsConstructor
class AttendancesRequestBody (
    val attendances: @NotNull List<Attendance>,
    val courseId: @Min(1) Int,
    val registeredTimestamp: @NotNull Date,
)
