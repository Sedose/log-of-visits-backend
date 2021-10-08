package com.example.work.controller

import com.example.work.controller.request.body.AttendancesRequestBody
import com.example.work.security.SecurityUser
import com.example.work.service.LecturerService
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance-register-file")
open class RegisterStudentAttendanceController (
    private val lecturerService: LecturerService,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("hasAuthority('attendances:write')")
    open fun registerAttendanceUsingFile(
        @RequestBody attendancesRequestBody: @Valid AttendancesRequestBody,
        authentication: Authentication,
    ): ResponseEntity<Void> {
        val securityUser = authentication.principal as SecurityUser
        lecturerService.registerAttendanceUsingFile(
            attendancesRequestBody,
            securityUser.id
        )
        return ResponseEntity.ok().build()
    }
}