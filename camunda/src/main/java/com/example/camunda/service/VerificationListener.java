package com.example.camunda.service;

import com.example.camunda.repository.AccountJpaRepository;
import com.example.camunda.repository.WorkFlowRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import javax.inject.Named;

@Service
@Named(value = "accountVerification")
public class VerificationListener implements JavaDelegate {

    private final WorkFlowRepository workFlowRepository;
    private final AccountJpaRepository jpaRepository;
    private final AccountValidationService validationService;
    private String messageId = null;

    public VerificationListener(WorkFlowRepository workFlowRepository, AccountJpaRepository jpaRepository, AccountValidationService validationService) {
        this.workFlowRepository = workFlowRepository;
        this.jpaRepository = jpaRepository;
        this.validationService = validationService;
    }

    @Override
    public void execute(DelegateExecution execution) {
        var processInstanceId = execution.getProcessInstanceId();
        workFlowRepository.findByProcessInstanceId(processInstanceId).ifPresentOrElse(workflowEntity -> {
                    messageId = workflowEntity.getMessageId();
                    jpaRepository.findByUuid(messageId).ifPresentOrElse(accountEntity -> {
                                var account = validationService.validateAccount(accountEntity);
                                if (account.getErrors().isEmpty())
                                    execution.setVariable("isValid", "true");
                                else
                                    execution.setVariable("isValid", "false");
                            }, () -> {
                                execution.setVariable("isValid", "false");
                                throw new IllegalArgumentException("Account cannot be found");
                            }
                    );

                }, () -> {
                    execution.setVariable("isValid", "false");
                    throw new IllegalArgumentException("Workflow cannot be executed");
                }
        );
    }
}
