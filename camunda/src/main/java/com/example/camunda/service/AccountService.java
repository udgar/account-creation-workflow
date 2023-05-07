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
        dto.setProcess(List.of(VERIFY_INFORMATION, RETOUCH, STOP));
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
        model.setProcess(nextProcess);
        return model;
    }

    public AccountDto get(String uuid) {
        var entity = jpaRepository.findByUuid(uuid).orElseThrow(IllegalAccessError::new);
        var model = mapper.model(entity);
        return model;
    }
}
