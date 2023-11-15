package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.User;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.model.AccountDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//todo
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto toDto(Account entity) {
        if (entity == null) {
            return null;
        }

        final AccountDto dto = new AccountDto();
        dto.setId(entity.getId());
        dto.setBalance(entity.getBalance());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        return dto;
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        final Account entity = new Account();
        entity.setBalance(accountDto.getBalance());

        if (accountDto.getUserId() != null) {
            User user = new User();
            user.setId(accountDto.getUserId());
            entity.setUser(user);
        }

        return entity;
    }

    @Override
    public List<AccountDto> toDto(List<Account> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> toEntity(List<AccountDto> accountDtoList) {
        if (accountDtoList == null) {
            return null;
        }

        return accountDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
