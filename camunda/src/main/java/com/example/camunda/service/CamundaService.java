package com.example.camunda.service;

import com.example.camunda.entity.WorkflowEntity;
import com.example.camunda.model.ProcessDetailsDto;
import com.example.camunda.model.ProcessInstanceDto;
import com.example.camunda.model.TaskDto;
import com.example.camunda.repository.WorkFlowRepository;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CamundaService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    private final WorkFlowRepository workFlowRepository;


    public CamundaService(RepositoryService repositoryService, RuntimeService runtimeService, TaskService taskService, WorkFlowRepository workFlowRepository) {
        this.repositoryService = repositoryService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.workFlowRepository = workFlowRepository;
    }

    public List<ProcessDetailsDto> getDeployedProcessDefinitions() {
        return repositoryService.createProcessDefinitionQuery().list().stream().map(ProcessDetailsDto::of).collect(Collectors.toList());
    }

    public ProcessInstanceDto startProcess(String key) {
        var process = ProcessInstanceDto.of(runtimeService.startProcessInstanceByKey(key));
        workFlowRepository.save(WorkflowEntity.of(process));
        return process;
    }

    public void resumeProcess(String instanceId) {
        var task = taskService.createTaskQuery().active().processInstanceId(instanceId).list().get(0);
        taskService.complete(task.getId());
    }

    public List<TaskDto> getActiveTasks() {
        return taskService.createTaskQuery().active().list().stream().map(TaskDto::of).collect(Collectors.toList());
    }
}
