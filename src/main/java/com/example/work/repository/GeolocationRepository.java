package com.example.work.repository;

import com.example.work.entity.UserCoordinatesEntity;
import org.springframework.data.repository.CrudRepository;

public interface GeolocationRepository extends CrudRepository<UserCoordinatesEntity, Integer> {

}
