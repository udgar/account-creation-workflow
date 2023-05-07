package com.example.camunda.service;

import com.example.camunda.entity.AccountEntity;
import com.example.camunda.mapper.AccountMapper;
import com.example.camunda.model.AccountDto;
import com.example.camunda.model.AccountRequest;
import com.example.camunda.model.Processes;
import com.example.camunda.repository.AccountJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.camunda.model.Processes.*;

@Service
public class AccountService {
    private final AccountJpaRepository jpaRepository;
    private final AccountWorkflowService workflowService;
    private final AccountMapper mapper;

    public AccountService(AccountJpaRepository jpaRepository, AccountWorkflowService workflowService, AccountMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.workflowService = workflowService;
        this.mapper = mapper;
    }

    public AccountDto initializeNewAccountCreation(AccountRequest request) {
        var entity = jpaRepository.save(extractEntity(request));
        workflowService.startWorkflow(entity.getUuid());
        var dto = mapper.model(entity);
        dto.setNextAction(List.of(VERIFY_INFORMATION, RETOUCH, STOP));
        entity.addNextAction(Arrays.asList(VERIFY_INFORMATION, RETOUCH, STOP));
        jpaRepository.save(entity);
        return dto;
    }

    private AccountEntity extractEntity(AccountRequest request) {
        var entity = mapper.requestEntity(request);
        entity.setUuid(UUID.randomUUID().toString());
        return entity;
    }

    public AccountDto completeProcess(String uuid, Processes process) {
        var entity = jpaRepository.findByUuid(uuid).orElseThrow(IllegalAccessError::new);
        var nextProcess = workflowService.completeProcess(process, entity.getUuid());
        var model = mapper.model(entity);
        entity.addNextAction(nextProcess);
        jpaRepository.save(entity);
        model.setNextAction(nextProcess);
        return model;
    }

    public AccountDto retouch(String uuid, AccountRequest request) {
        var entity = jpaRepository.findByUuid(uuid).orElseThrow(IllegalAccessError::new);
        return mapper.model(updateEntity(entity, request));
    }

    private AccountEntity updateEntity(AccountEntity entity, AccountRequest request) {
        Optional.ofNullable(request.getUsername()).ifPresent(entity::setUsername);
        Optional.ofNullable(request.getPassword()).ifPresent(entity::setPassword);
        Optional.ofNullable(request.getFirstName()).ifPresent(entity::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(entity::setLastName);
        Optional.ofNullable(request.getAddressLine()).ifPresent(entity::setAddressLine);
        Optional.ofNullable(request.getAddressLine()).ifPresent(entity::setAddressLine);
        Optional.ofNullable(request.getCitizenshipNumber()).ifPresent(entity::setCitizenshipNumber);
        Optional.ofNullable(request.getAge()).ifPresent(entity::setAge);
        Optional.ofNullable(request.getAccountType()).ifPresent(entity::setAccountType);
        Optional.ofNullable(request.getInitialAmount()).ifPresent(entity::setInitialAmount);
        Optional.ofNullable(request.getMiddleName()).ifPresent(entity::setMiddleName);
        return jpaRepository.save(entity);
    }

    public AccountDto get(String uuid) {
        var entity = jpaRepository.findByUuid(uuid).orElseThrow(IllegalAccessError::new);
        var model = mapper.model(entity);
        return model;
    }
}
