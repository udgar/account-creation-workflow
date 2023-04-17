package com.example.camunda.controller;

import com.example.camunda.model.AccountRequest;
import com.example.camunda.model.Processes;
import com.example.camunda.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUserAccount(@RequestBody AccountRequest request) {
        var message = service.initializeNewAccountCreation(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping(value = "/{uuid}/complete/{process}")
    public ResponseEntity<?> completeProcess(@PathVariable String uuid, @PathVariable String process) {
        var account = service.completeProcess(uuid, Processes.valueOf(process));
        return ResponseEntity.ok(account);
    }
}
