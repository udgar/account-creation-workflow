package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.camunda.model.Processes.*;

@Component
public class AccountCreationListener extends AccountWorkflowProcessListener {

    protected AccountCreationListener(TaskService taskService) {
        super(taskService);
    }

    @Override
    public Processes getProcess() {
        return CREATE_ACCOUNT;
    }

    @Override
    public List<Processes> getNextAction(String processInstanceId) {
        return List.of(APPROVE_CREATION, CANCEL_CREATION);
    }

}
