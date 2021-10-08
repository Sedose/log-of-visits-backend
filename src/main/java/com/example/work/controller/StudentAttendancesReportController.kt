package com.example.work.controller

import com.example.work.response.body.StudentAttendancesReport
import com.example.work.security.SecurityUser
import com.example.work.service.TrainingRepresentativeService
import lombok.Setter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Setter
@RestController
@RequestMapping("api/student-attendances-report")
open class StudentAttendancesReportController (
    @Autowired
    @Qualifier("trainingRepresentativeService")
    private val trainingRepresentativeService: TrainingRepresentativeService,
) {

    @GetMapping
    @PreAuthorize("hasAuthority('attendances-report:read')")
    open fun getAttendancesReportController(
        @RequestParam studentGroupId: Int,
        @RequestParam courseId: Int,
        authentication: Authentication,
    ): StudentAttendancesReport {
        return trainingRepresentativeService.formStudentAttendancesReportByGroupIdAndCourseId(
            studentGroupId,
            courseId,
            (authentication.principal as SecurityUser).id.toLong()
        )
    }
}