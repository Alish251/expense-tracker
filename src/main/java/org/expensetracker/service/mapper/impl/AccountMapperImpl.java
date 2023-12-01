package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.User;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.UserDto;
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
            UserDto userDto = new UserDto();
            userDto.setId(entity.getUser().getId());
            userDto.setEmail(entity.getUser().getEmail());
            userDto.setFirstname(entity.getUser().getFirstname());
            userDto.setLastname(entity.getUser().getLastname());
            dto.setUser(userDto);

        }

        return dto;
    }

    @Override
    public Account toEntity(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        final Account entity = new Account();
        entity.setId(accountDto.getId());
        entity.setBalance(accountDto.getBalance());

        if (accountDto.getUser() != null) {
            User user = new User();
            user.setId(accountDto.getUser().getId());
            user.setEmail(accountDto.getUser().getEmail());
            user.setFirstname(accountDto.getUser().getFirstname());
            user.setLastname(accountDto.getUser().getLastname());
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
