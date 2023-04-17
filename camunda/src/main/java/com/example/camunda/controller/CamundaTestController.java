package com.example.camunda.controller;

import com.example.camunda.model.ProcessDetailsDto;
import com.example.camunda.model.ProcessInstanceDto;
import com.example.camunda.model.TaskDto;
import com.example.camunda.service.CamundaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camunda")
public class CamundaTestController {

    private final CamundaService camundaService;

    public CamundaTestController(CamundaService camundaService) {
        this.camundaService = camundaService;
    }

    @GetMapping(value = "/process-definitions")
    public List<ProcessDetailsDto> getProcessDetails() {
        return camundaService.getDeployedProcessDefinitions();

    }

    @PostMapping(value = "start/{key}")
    @ResponseStatus(HttpStatus.OK)
    public ProcessInstanceDto startProcess(@PathVariable String key) {
        return camundaService.startProcess(key);
    }

    @PostMapping(value = "resume/{instanceId}")
    @ResponseStatus(HttpStatus.OK)
    public void resumeProcess(@PathVariable String instanceId) {
        camundaService.resumeProcess(instanceId);
    }

    @GetMapping(value = "tasks/active")
    public List<TaskDto> getProcessInstances() {
        return camundaService.getActiveTasks();
    }
}
