package com.example.work.controller;

import com.example.work.controller.request.body.CoordinatesRequestBody;
import com.example.work.service.GeolocationService;
import com.example.work.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/coordinates")
public class GeolocationRestController {

    private final GeolocationService geolocationService;

    @PutMapping
    public void updateLocation(
            @RequestBody CoordinatesRequestBody coordinatesRequestBody,
            Authentication authentication
    ) {
        geolocationService.updateLocation(coordinatesRequestBody, ((SecurityUser)authentication.getPrincipal()).getId());
    }
}
