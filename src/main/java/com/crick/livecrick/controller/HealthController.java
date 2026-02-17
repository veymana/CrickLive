package com.crick.livecrick.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthController {

    @GetMapping("/")
    public MessageResponse welcome() {
        return new MessageResponse("Welcome to LiveCrick API");
    }

    @GetMapping("/health")
    public StatusResponse health() {
        return new StatusResponse("UP");
    }

    @GetMapping("/health/ready")
    public StatusResponse readiness() {
        return new StatusResponse("READY");
    }

    public record MessageResponse(String message) {
    }

    public record StatusResponse(String status) {
    }
}

