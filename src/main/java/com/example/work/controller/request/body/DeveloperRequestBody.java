package com.example.work.controller.request.body;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeveloperRequestBody {
    Long id;
    String firstName;
    String lastName;
}
