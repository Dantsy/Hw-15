package ru.otus.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class SimpleRestController {

    @GetMapping(path = "api/v1/getTestString")
    public ResponseEntity<String> getTestString() {
        return ResponseEntity.ok("Current time is " + LocalDateTime.now() + ". Random number is " + (int) (Math.random() * 100) + ".");
    }
}
