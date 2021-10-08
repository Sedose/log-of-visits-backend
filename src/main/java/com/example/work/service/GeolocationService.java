package com.example.work.service;

import com.example.work.controller.request.body.CoordinatesRequestBody;
import com.example.work.entity.UserCoordinatesEntity;
import com.example.work.repository.GeolocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeolocationService {

    private final GeolocationRepository geolocationRepository;

    public void updateLocation(CoordinatesRequestBody coordinatesRequestBody, Integer userId) {
        geolocationRepository.save(new UserCoordinatesEntity(
                userId, coordinatesRequestBody.getLatitude(), coordinatesRequestBody.getLongitude(), !geolocationRepository.existsById(userId)
        ));
    }
}
