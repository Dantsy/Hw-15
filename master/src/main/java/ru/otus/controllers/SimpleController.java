package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.services.SlaveService;

@RestController
@RequiredArgsConstructor
public class SimpleController {

    private final SlaveService slaveService;

    @GetMapping(path = "api/v1/test")
    public ResponseEntity<String> getTestMethod() {
        try {
            return new ResponseEntity<>(
                    "Slave service response is: %s".formatted(slaveService.getSlaveServiceResponse()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Something went wrong. Error message: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}