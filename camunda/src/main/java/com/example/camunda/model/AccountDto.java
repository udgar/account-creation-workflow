package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountDto {

    private String uuid;

    private String username;
    private String password;
    private String firstName;

    private String middleName;
    private String lastName;
    private String addressLine;
    private String citizenshipNumber;

    private List<Processes> process;
}
