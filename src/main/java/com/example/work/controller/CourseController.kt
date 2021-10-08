package com.example.work.controller

import com.example.work.response.body.Course
import com.example.work.security.SecurityUser
import com.example.work.service.LecturerService
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
open class CourseController (
    private val lecturerService: LecturerService,
) {

    @GetMapping
    @PreAuthorize("hasAuthority('courses:read')")
    open fun getAllCourses(authentication: Authentication): ResponseEntity<Iterable<Course>> {
        val securityUser = authentication.principal as SecurityUser
        return ResponseEntity.ok(
            lecturerService.retrieveAllCoursesByLecturerId(securityUser.id)
        )
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasAuthority('courses:read')")
    open fun getCourse(@PathVariable courseId: Int): ResponseEntity<Course> {
        return ResponseEntity.ok(lecturerService.findCourseById(courseId))
    }
}
