package com.example.camunda.listener;

import com.example.camunda.model.Processes;
import org.camunda.bpm.engine.TaskService;

import java.util.List;

public interface ProcessListener {

    Processes getProcess();

    List<Processes> completeTask(String processInstanceId);

    List<Processes> getNextAction(String processInstanceId);


}
