package com.example.camunda.entity;

import com.example.camunda.model.AccountType;
import com.example.camunda.model.Processes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String username;
    private String password;
    private String firstName;

    private String middleName;
    private String lastName;
    private String addressLine;
    private String citizenshipNumber;
    private AccountType accountType;
    @ElementCollection
    private List<Processes> nextAction;
}
