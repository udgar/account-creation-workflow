package com.example.camunda.entity;

import com.example.camunda.model.AccountType;
import com.example.camunda.model.Processes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private Integer age;
    private String addressLine;
    private String citizenshipNumber;
    private AccountType accountType;
    @ElementCollection
    private List<Processes> nextAction;
    @ElementCollection
    private List<String> errors = new ArrayList<>();
    private BigDecimal initialAmount;

    public void addError(String error) {
        this.errors.add(error);
    }
}
