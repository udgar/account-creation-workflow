package com.example.camunda.repository;

import com.example.camunda.entity.WorkflowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkFlowRepository extends JpaRepository<WorkflowEntity, Long> {

    Optional<WorkflowEntity> findByProcessInstanceId(String processInstanceId);
    Optional<WorkflowEntity> findByMessageId(String messageId);
}
