package org.expensetracker.service.impl;

import java.util.ArrayList;
import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.UserService;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final AccountService accountService;

    public UserServiceImpl(UserRepository repository, UserMapper mapper,
                           AccountService accountService) {
        this.repository = repository;
        this.mapper = mapper;
        this.accountService = accountService;
    }

    @Override
    public List<UserDto> getAll() {
        Optional<List<User>> optionalUsers = repository.findAll();
        List<User> users = optionalUsers.orElseThrow(() -> new RuntimeException("No users found"));
        return mapper.toDto(users);
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("No user found"));
        return mapper.toDto(user);
    }

    @Override
    public UserDto add(UserDto userDto) {
        final List<AccountDto> accounts = new ArrayList<>();
        if (userDto.getAccounts() != null && !userDto.getAccounts().isEmpty()) {
            for (AccountDto account: userDto.getAccounts()) {
                AccountDto dto = accountService.add(account);
                accounts.add(dto);
            }
            userDto.setAccounts(accounts);
        }
        User user = mapper.toEntity(userDto);
        User savedUser = repository.add(user).orElseThrow(() -> new RuntimeException("User not added"));
        UserDto savedUserDto = mapper.toDto(savedUser);
        if (!accounts.isEmpty()) {
            for (AccountDto accountDto : accounts) {
                accountDto.setUser(savedUserDto);
                accountService.updateById(accountDto.getId(), accountDto);
            }
        }
        return savedUserDto;
    }

    @Override
    public UserDto updateById(Long id, UserDto userDto) {
        User user = repository.updateById(id, mapper.toEntity(userDto)).orElseThrow(() -> new RuntimeException("User not updated"));

        return mapper.toDto(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id).orElseThrow(() -> new RuntimeException("No user found"));
    }
}
