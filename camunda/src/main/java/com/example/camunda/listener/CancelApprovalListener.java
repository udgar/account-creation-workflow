package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.camunda.model.Processes.*;

@Component
public class CancelApprovalListener extends AccountWorkflowProcessListener{
    protected CancelApprovalListener(TaskService taskService) {
        super(taskService);
    }

    @Override
    public Processes getProcess() {
        return CANCEL_APPROVAL;
    }

    @Override
    public List<Processes> getNextAction(String processInstanceId) {
        return List.of(VERIFY_INFORMATION,RETOUCH,STOP);
    }
}
