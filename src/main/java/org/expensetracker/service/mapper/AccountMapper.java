package org.expensetracker.service.mapper;

import org.expensetracker.database.entity.Account;
import org.expensetracker.service.model.AccountDto;

import java.util.List;

public interface AccountMapper {
    AccountDto toDto(Account entity);

    Account toEntity(AccountDto accountDto);

    List<AccountDto> toDto(List<Account> entities);

    List<Account> toEntity(List<AccountDto> accountDtoList);
}
