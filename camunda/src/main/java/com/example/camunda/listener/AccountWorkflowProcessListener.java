package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;

import java.util.List;

public abstract class AccountWorkflowProcessListener implements ProcessListener {

    private final TaskService taskService;

    protected AccountWorkflowProcessListener(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<Processes> completeTask(String processInstanceId) {
        Task task = taskService.createTaskQuery().active().processInstanceId(processInstanceId).list().get(0);
        taskService.setVariable(task.getId(), "process", process().name());
        taskService.complete(task.getId());
        return nextAction(processInstanceId);
    }

    private Processes process() {
        return getProcess();
    }

    private List<Processes> nextAction(String processInstanceId) {
        return getNextAction(processInstanceId);
    }

    @Override
    abstract public Processes getProcess();

    @Override
    abstract public List<Processes> getNextAction(String processInstanceId);
}
