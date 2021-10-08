package com.example.work.controller

import com.example.work.service.TrainingRepresentativeService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/specialties")
open class SpecialitiesController(
    val trainingRepresentativeService: TrainingRepresentativeService,
) {

    @GetMapping
    fun getAllSpecialities() =
        trainingRepresentativeService.findAllSpecialities()
}
