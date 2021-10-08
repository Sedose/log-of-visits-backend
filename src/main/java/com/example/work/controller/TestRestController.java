package com.example.work.controller;

import com.example.work.controller.request.body.DeveloperRequestBody;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/developerRequestBodies")
public class TestRestController {

    private final List<DeveloperRequestBody> developerRequestBodies = new ArrayList<>() {{
        addAll(List.of(
                DeveloperRequestBody.builder()
                        .id(1L)
                        .firstName("Sarah")
                        .lastName("Jones")
                        .build(),
                DeveloperRequestBody.builder()
                        .id(2L)
                        .firstName("Evan")
                        .lastName("Lu")
                        .build(),
                DeveloperRequestBody.builder()
                        .id(3L)
                        .firstName("Sobaken")
                        .lastName("Jowaken")
                        .build()
        ));
    }};

    @GetMapping
    public List<DeveloperRequestBody> getAll() {
        return developerRequestBodies;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developerRequestBodies:write')")
    public DeveloperRequestBody create(@RequestBody DeveloperRequestBody developerRequestBody) {
        developerRequestBodies.add(developerRequestBody);
        return developerRequestBody;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developerRequestBodies:write')")
    public void deleteById(@PathVariable Long id) {
        developerRequestBodies.removeIf(developerRequestBody -> developerRequestBody.getId().equals(id));
    }
}
