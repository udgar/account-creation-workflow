package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.task.Task;

import java.util.Date;

@Getter
@Setter
public class TaskDto {

    private String id;
    private String name;
    private String assignee;
    private Date created;
    private String processInstanceId;
    private String taskDefinitionKey;

    public TaskDto(){

    }

    public TaskDto(String id, String name, String assignee, Date created, String processInstanceId, String taskDefinitionKey) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.created = created;
        this.processInstanceId = processInstanceId;
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public static TaskDto of(Task task){
        return new TaskDto(task.getId(),task.getName(),task.getAssignee(),task.getCreateTime()
                ,task.getProcessInstanceId(),task.getTaskDefinitionKey());
    }
}
