package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.User;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        final UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setFirstname(entity.getFirstname());
        userDto.setLastname(entity.getLastname());

        /*if (entity.getAccounts() != null && !entity.getAccounts().isEmpty()) {
            List<AccountDto> accountDtoSet = new ArrayList<>();
            for (Account account : entity.getAccounts()) {
                AccountDto accountDto = new AccountDto();
                accountDto.setBalance(account.getBalance());
                accountDto.setUser(userDto); //��������� ����������������� - ��������!
                accountDtoSet.add(accountDto);
            }

            userDto.setAccounts(accountDtoSet);
        }*/
        AccountMapper accountMapper = new AccountMapperImpl();
        if (entity.getAccounts() != null && !entity.getAccounts().isEmpty()) {
            List<AccountDto> accountDtoSet = new ArrayList<>();
            for (Account account : entity.getAccounts()) {
                accountDtoSet.add(accountMapper.toDto(account));
            }
            userDto.setAccounts(accountDtoSet);
        }

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        final User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());

        /*if (userDto.getAccounts() != null && !userDto.getAccounts().isEmpty()) {
            List<Account> accounts = new ArrayList<>();
            for (AccountDto dto : userDto.getAccounts()) {
                Account account = new Account();
                account.setId(dto.getId());
                account.setBalance(dto.getBalance());
                account.setUser(user);
                accounts.add(account);
            }
            user.setAccounts(accounts);
        }*/

        AccountMapper accountMapper = new AccountMapperImpl();
        if (userDto.getAccounts() != null && !userDto.getAccounts().isEmpty()) {
            List<Account> accounts = new ArrayList<>();
            for (AccountDto dto : userDto.getAccounts()) {
                Account account = accountMapper.toEntity(dto);
                accounts.add(account);
            }
            user.setAccounts(accounts);
        }
        return user;
    }

    @Override
    public List<UserDto> toDto(List<User> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserDto> userDtoList) {
        if (userDtoList == null) {
            return null;
        }
        return userDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
