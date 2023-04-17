package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;

@Getter
@Setter
public class ProcessDetailsDto {

    private String id;
    private String name;
    private String key;

    private String category;

    private String tenantId;

    private int version;

    public ProcessDetailsDto() {
    }


    public ProcessDetailsDto(String id, String name, String key, String category, String tenantId, int version) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.category = category;
        this.tenantId = tenantId;
        this.version = version;
    }

    public static ProcessDetailsDto of(ProcessDefinition processDefinition) {
        ProcessDefinitionEntity entity = (ProcessDefinitionEntity) processDefinition;
        return new ProcessDetailsDto(entity.getId(), entity.getName(), entity.getKey(), entity.getCategory()
                , entity.getTenantId(), entity.getVersion());
    }
}
