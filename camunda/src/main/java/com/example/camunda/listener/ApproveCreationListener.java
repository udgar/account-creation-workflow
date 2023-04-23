package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.camunda.model.Processes.CANCEL_APPROVAL;

@Component
public class ApproveCreationListener extends AccountWorkflowProcessListener {
    protected ApproveCreationListener(TaskService taskService) {
        super(taskService);
    }

    @Override
    public Processes getProcess() {
        return Processes.APPROVE_CREATION;
    }

    @Override
    public List<Processes> getNextAction(String processInstanceId) {
        return List.of(Processes.APPROVE_ACCOUNT, CANCEL_APPROVAL);
    }
}
