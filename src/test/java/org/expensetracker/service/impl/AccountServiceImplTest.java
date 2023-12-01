package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository repository;
    @Mock
    private AccountMapper mapper;
    @InjectMocks
    private AccountServiceImpl accountService;

    private UserDto userDto;
    private User user;

    private AccountDto accountDto;
    private Account account;
    private Long id;

    @BeforeEach
    public void init() {
        id = 1L;

        userDto = new UserDto();
        userDto.setFirstname("Test Name");
        userDto.setLastname("Test Lastname");
        userDto.setEmail("Test Email");

        user = new User();
        user.setId(id);
        user.setFirstname("Test Name");
        user.setLastname("Test Lastname");
        user.setEmail("Test Email");

        accountDto = new AccountDto();
        accountDto.setId(id);
        accountDto.setBalance(BigDecimal.valueOf(1000));
        accountDto.setUser(userDto);

        account = new Account();
        account.setId(id);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUser(user);
    }

    @Test
    public void getAll() {
        var entities = Collections.singletonList(account);
        when(repository.findAll()).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(Collections.singletonList(accountDto));

        var result = accountService.getAll();

        assertEquals(Collections.singletonList(accountDto), result);

        verify(repository).findAll();
        verify(mapper).toDto(entities);
    }

    @Test
    public void getById() {
        when(repository.findById(id)).thenReturn(Optional.of(account));
        when(mapper.toDto(account)).thenReturn(accountDto);
        var result = accountService.getById(id);
        assertEquals(accountDto, result);

        verify(repository).findById(id);
        verify(mapper).toDto(account);
    }

    @Test
    void whenNoAccount_thenThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> accountService.getById(id));
    }

    @Test
    void add() {
        Account mappedAccount = new Account();
        mappedAccount.setBalance(BigDecimal.valueOf(100));
        mappedAccount.setUser(user);

        when(mapper.toEntity(accountDto)).thenReturn(mappedAccount);
        when(repository.add(mappedAccount)).thenReturn(Optional.of(account));
        accountDto.setId(id);

        when(mapper.toDto(account)).thenReturn(accountDto);

        var result = accountService.add(accountDto);

        assertEquals(accountDto, result);
        verify(mapper).toEntity(accountDto);
        verify(repository).add(mappedAccount);
        verify(mapper).toDto(account);
    }

    @Test
    void updateById() {
        Account updatedAccount = new Account();
        updatedAccount.setBalance(BigDecimal.valueOf(100));
        updatedAccount.setUser(user);

        AccountDto updatedAccountDto = new AccountDto();
        updatedAccountDto.setBalance(BigDecimal.valueOf(100));
        updatedAccountDto.setUser(userDto);

        when(repository.updateById(id, mapper.toEntity(updatedAccountDto)))
                .thenReturn(Optional.of(updatedAccount));
        when(mapper.toDto(updatedAccount)).thenReturn(updatedAccountDto);

        var result = accountService.updateById(id, updatedAccountDto);

        assertEquals(updatedAccountDto, result);
        verify(repository).updateById(id, mapper.toEntity(updatedAccountDto));
        verify(mapper).toDto(updatedAccount);
    }

    @Test
    void deleteById() {
        when(repository.deleteById(id)).thenReturn(Optional.of(id));

        accountService.deleteById(id);

        verify(repository).deleteById(id);
    }
}
