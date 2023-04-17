package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.camunda.model.Processes.*;

@Component
public class VerificationProcessListener extends AccountWorkflowProcessListener {

    private final TaskService taskService;

    protected VerificationProcessListener(TaskService taskService, TaskService taskService1) {
        super(taskService);
        this.taskService = taskService1;
    }

    @Override
    public Processes getProcess() {
        return VERIFY_INFORMATION;
    }

    @Override
    public List<Processes> getNextAction(String processInstanceId) {
        var task = this.taskService.createTaskQuery().active().processInstanceId(processInstanceId).list().get(0);
        if (taskService.getVariable(task.getId(), "isValid") == "false") {
            return List.of(RETOUCH, VERIFY_INFORMATION);
        }
        return List.of(CREATE_ACCOUNT);
    }
}
