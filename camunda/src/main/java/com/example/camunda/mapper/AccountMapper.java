package com.example.camunda.mapper;

import com.example.camunda.model.AccountDto;
import com.example.camunda.entity.AccountEntity;
import com.example.camunda.model.AccountRequest;

@org.mapstruct.Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto model(AccountEntity entity);

    AccountEntity entity(AccountDto dto);

    AccountEntity requestEntity(AccountRequest request);
}
