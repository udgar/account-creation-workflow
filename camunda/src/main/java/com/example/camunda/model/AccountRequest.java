package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

    private String username;
    private String password;
    private String firstName;

    private String middleName;
    private String lastName;
    private String addressLine;
    private String citizenshipNumber;
    private AccountType accountType;


}
