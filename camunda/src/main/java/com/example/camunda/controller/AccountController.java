package com.example.camunda.controller;

import com.example.camunda.model.AccountDto;
import com.example.camunda.model.AccountRequest;
import com.example.camunda.model.Processes;
import com.example.camunda.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        addLinks(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping(value = "/{uuid}/complete/{process}")
    public ResponseEntity<?> completeProcess(@PathVariable String uuid, @PathVariable String process) {
        var account = service.completeProcess(uuid, Processes.valueOf(process));
        addLinks(account);
        return ResponseEntity.ok(account);
    }

    @PostMapping(value = "/{uuid}/complete/RETOUCH")
    public ResponseEntity<?> completeRetouchProcess(@PathVariable String uuid, @RequestBody AccountRequest request) {
        var account = service.retouch(uuid, request);
        addLinks(account);
        return ResponseEntity.ok(account);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<AccountDto> get(@PathVariable String uuid) {
        var account = service.get(uuid);
        addLinks(account);
        return ResponseEntity.ok(account);
    }

    private void addLinks(AccountDto accountDto) {
        accountDto.add(linkTo(methodOn(AccountController.class).get(accountDto.getUuid())).withSelfRel());
        for (var process : accountDto.getNextAction())
            accountDto.add(linkTo(methodOn(AccountController.class).completeProcess(accountDto.getUuid(),
                    process.name())).withRel(process.name()));
    }
}
