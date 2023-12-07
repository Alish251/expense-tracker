package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Income;
import org.expensetracker.database.entity.IncomeSource;
import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.IncomeRepository;
import org.expensetracker.service.mapper.IncomeMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.IncomeDto;
import org.expensetracker.service.model.IncomeSourceDto;
import org.expensetracker.service.model.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncomeServiceImplTest {
    @Mock
    private IncomeRepository repository;
    @Mock
    private IncomeMapper mapper;
    @InjectMocks
    private IncomeServiceImpl service;

    @Mock
    private AccountServiceImpl accountService;

    private Income income;
    private IncomeDto incomeDto;

    private Long id;


    private Account account;

    private IncomeSource incomeSource;


    @BeforeEach
    public void init() {
        id = 1L;

        UserDto userDto = new UserDto();
        userDto.setFirstname("Test Name");
        userDto.setLastname("Test Lastname");
        userDto.setEmail("Test Email");

        User user = new User();
        user.setId(id);
        user.setFirstname("Test Name");
        user.setLastname("Test Lastname");
        user.setEmail("Test Email");

        AtomicReference<AccountDto> accountDto = new AtomicReference<>(new AccountDto());
        accountDto.get().setId(id);
        accountDto.get().setBalance(BigDecimal.valueOf(1000));
        accountDto.get().setUser(userDto);

        account = new Account();
        account.setId(id);
        account.setBalance(BigDecimal.valueOf(1000));
        account.setUser(user);

        incomeSource = new IncomeSource();
        incomeSource.setId(id);
        incomeSource.setName("Test Name");
        incomeSource.setDescription("Test Description");

        IncomeSourceDto incomeSourceDto = new IncomeSourceDto();
        incomeSourceDto.setName("Test Name");
        incomeSourceDto.setDescription("Test Description");


        income = new Income();
        income.setId(id);
        income.setDate(LocalDate.now());
        income.setAmount(BigDecimal.valueOf(100));
        income.setAccount(account);
        income.setIncomeSource(incomeSource);


        incomeDto = new IncomeDto();
        incomeDto.setDate(LocalDate.now());
        incomeDto.setAmount(BigDecimal.valueOf(100));
        incomeDto.setAccountId(account.getId());
        incomeDto.setIncomeSourceId(incomeSource.getId());

    }

    @Test
    void getAll() {
        var entities = Collections.singletonList(income);
        var dtos = Collections.singletonList(incomeDto);

        when(repository.findAll()).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(dtos);

        var result = service.getAll();

        assertEquals(dtos, result);
        verify(repository).findAll();
        verify(mapper).toDto(entities);
    }

    @Test
    void getById() {
        when(repository.findById(id)).thenReturn(Optional.of(income));
        when(mapper.toDto(income)).thenReturn(incomeDto);
        var result = service.getById(id);
        assertEquals(incomeDto, result);

        verify(repository).findById(id);
        verify(mapper).toDto(income);
    }

    @Test
    void getAllByAccountId() {
        var entities = Collections.singletonList(income);
        var dtos = Collections.singletonList(incomeDto);

        when(repository.findByAccountId(id)).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(dtos);

        var result = service.getAllByAccountId(id);

        assertEquals(dtos, result);
        verify(repository).findByAccountId(id);
        verify(mapper).toDto(entities);
    }

    @Test
    void add() {
        Income mappedIncome = new Income();
        mappedIncome.setAmount(BigDecimal.valueOf(150));
        mappedIncome.setIncomeSource(incomeSource);
        mappedIncome.setAccount(account);
        mappedIncome.setDate(LocalDate.now());

        when(mapper.toEntity(incomeDto)).thenReturn(mappedIncome);
        when(repository.add(mappedIncome)).thenReturn(Optional.of(income));
        incomeDto.setId(id);

        when(mapper.toDto(income)).thenReturn(incomeDto);

        var result = service.add(incomeDto);

        assertEquals(incomeDto, result);
        verify(mapper).toEntity(incomeDto);
        verify(repository).add(mappedIncome);
        verify(mapper).toDto(income);
    }

    @Test
    void whenNoIncome_thenThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(id));
    }


    @Test
    void updateById() {
        Income updatedIncome = new Income();
        updatedIncome.setId(id);
        updatedIncome.setAmount(BigDecimal.valueOf(199));
        updatedIncome.setDate(LocalDate.now());
        updatedIncome.setIncomeSource(incomeSource);
        updatedIncome.setAccount(account);

        IncomeDto updatedIncomeDto = new IncomeDto();
        updatedIncomeDto.setAmount(BigDecimal.valueOf(199));
        updatedIncomeDto.setDate(LocalDate.now());
        updatedIncomeDto.setIncomeSourceId(incomeSource.getId());
        updatedIncomeDto.setAccountId(account.getId());

        when(repository.findById(id))
                .thenReturn(Optional.of(income));
        when(repository.updateById(id, mapper.toEntity(updatedIncomeDto)))
                .thenReturn(Optional.of(updatedIncome));
        when(mapper.toDto(updatedIncome)).thenReturn(updatedIncomeDto);

        var result = service.updateById(id, updatedIncomeDto);

        assertEquals(updatedIncomeDto, result);
        verify(repository).updateById(id, mapper.toEntity(updatedIncomeDto));
        verify(mapper).toDto(updatedIncome);

    }

    @Test
    void deleteById() {
        when(repository.findById(id)).thenReturn(Optional.of(income));
        when(repository.deleteById(id)).thenReturn(Optional.of(id));

        service.deleteById(id);

        verify(repository).deleteById(id);
    }
}