package com.example.camunda.service;

import com.example.camunda.entity.WorkflowEntity;
import com.example.camunda.listener.ProcessListener;
import com.example.camunda.model.Processes;
import com.example.camunda.repository.WorkFlowRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountWorkflowService {

    private final WorkFlowRepository repository;
    private final RuntimeService runtimeService;

    private Map<Processes, ProcessListener> processes;

    public AccountWorkflowService(WorkFlowRepository repository, RuntimeService runtimeService, List<ProcessListener> processes) {
        this.repository = repository;
        this.runtimeService = runtimeService;
        this.processes = map(processes);
    }

    private Map<Processes, ProcessListener> map(List<ProcessListener> processes) {
        var map = new HashMap<Processes, ProcessListener>();
        processes.forEach(listener -> {
            map.put(listener.getProcess(), listener);
        });
        return map;
    }

    public void startWorkflow(String uuid) {
        try {
            var process = runtimeService.startProcessInstanceByKey("CREATE_NEW_ACCOUNT");
            repository.save(newEntity(process, uuid));
        } catch (Exception e) {
            throw new RuntimeException("Could not start the workflow");
        }

    }

    public List<Processes> completeProcess(Processes process, String messageId) {
        var instanceId = repository.findByMessageId(messageId).map(WorkflowEntity::getProcessInstanceId).orElseThrow(IllegalArgumentException::new);
        return processes.get(process).completeTask(instanceId);
    }

    private WorkflowEntity newEntity(ProcessInstance instance, String uuid) {
        WorkflowEntity entity = new WorkflowEntity();
        entity.setBusinessKey(instance.getBusinessKey());
        entity.setMessageId(uuid);
        entity.setProcessInstanceId(instance.getProcessInstanceId());
        return entity;
    }


}
