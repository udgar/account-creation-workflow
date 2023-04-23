package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerifyStopListener extends AccountWorkflowProcessListener{
    protected VerifyStopListener(TaskService taskService) {
        super(taskService);
    }

    @Override
    public Processes getProcess() {
        return Processes.VERIFY_STOP;
    }

    @Override
    public List<Processes> getNextAction(String processInstanceId) {
        return List.of();
    }
}
