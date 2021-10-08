package com.example.work.response.body

sealed class StudentAttendancesReport

data class StudentAttendancesReportFull(
    val items: List<ReportItem>,
    val lecturerRegisteredBy: LecturerRegisteredBy,
): StudentAttendancesReport()

object StudentAttendancesReportEmpty : StudentAttendancesReport()

class ReportItem(
    val email: String,
    val firstName: String,
    val middleName: String?,
    val lastName: String?,
    val attendancesPercent: Int,
)

class LecturerRegisteredBy(
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
)
