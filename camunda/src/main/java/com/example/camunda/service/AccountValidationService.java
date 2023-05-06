package com.example.camunda.service;

import com.example.camunda.entity.AccountEntity;
import com.example.camunda.repository.AccountJpaRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class AccountValidationService {

    private final KieContainer container;
    private final AccountJpaRepository repository;

    public AccountValidationService(KieContainer container, AccountJpaRepository repository) {
        this.container = container;
        this.repository = repository;
    }

    public AccountEntity validateAccount(AccountEntity entity) {
        KieSession session = container.newKieSession();
        session.setGlobal("account", entity);
        session.insert(entity);
        session.fireAllRules();
        session.dispose();
        return repository.save(entity);
    }
}
