package com.example.camunda.entity;


import com.example.camunda.model.ProcessInstanceDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "APPLICATION_WORKFLOW")
@Getter
@Setter
public class WorkflowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessKey;

    private String messageId;

    private String processInstanceId;


    public WorkflowEntity() {
    }

    public WorkflowEntity(String businessKey, String messageId, String processInstanceId) {
        this.businessKey = businessKey;
        this.messageId = messageId;
        this.processInstanceId = processInstanceId;
    }

    public static WorkflowEntity of(ProcessInstanceDto dto) {
        return new WorkflowEntity(dto.getBusinessKey(), UUID.randomUUID().toString(), dto.getProcessInstanceId());
    }

}
