package com.example.camunda.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AccountDto extends RepresentationModel<AccountDto> {

    private String uuid;

    private String username;
    private String password;
    private String firstName;
    private Integer age;

    private String middleName;
    private String lastName;
    private String addressLine;
    private String citizenshipNumber;
    private AccountType accountType;

    private List<Processes> nextAction;
    private List<String> errors;
    private BigDecimal initialAmount;
}
