package com.example.demo.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StateRequest {

    @NotBlank
    private String statename;
}
