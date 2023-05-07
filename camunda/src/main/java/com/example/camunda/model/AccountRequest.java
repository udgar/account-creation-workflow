package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {

    private String username;
    private String password;
    private String firstName;
    private Integer age;
    private String middleName;
    private String lastName;
    private String addressLine;
    private String citizenshipNumber;
    private AccountType accountType;
    private BigDecimal initialAmount;


}
