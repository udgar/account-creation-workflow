package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.runtime.ProcessInstance;

@Getter
@Setter
public class ProcessInstanceDto {

    private String processDefinitionId;
    private String businessKey;
    private String rootProcessInstanceId;
    private String caseInstanceId;
    private String processInstanceId;
    private boolean isSuspended;


    public ProcessInstanceDto() {
    }

    public ProcessInstanceDto(String processDefinitionId, String businessKey, String rootProcessInstanceId, String caseInstanceId, String processInstanceId, boolean isSuspended) {
        this.processDefinitionId = processDefinitionId;
        this.businessKey = businessKey;
        this.rootProcessInstanceId = rootProcessInstanceId;
        this.caseInstanceId = caseInstanceId;
        this.isSuspended = isSuspended;
        this.processInstanceId = processInstanceId;
    }

    public static ProcessInstanceDto of(ProcessInstance instance) {
        return new ProcessInstanceDto(instance.getProcessDefinitionId(), instance.getBusinessKey()
                , instance.getRootProcessInstanceId(), instance.getCaseInstanceId(), instance.getProcessInstanceId(), instance.isSuspended());
    }
}
