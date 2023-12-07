package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.ExpenseRepository;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.CategoryDto;
import org.expensetracker.service.model.ExpenseDto;
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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {
    @Mock
    private ExpenseRepository repository;
    @Mock
    private ExpenseMapper mapper;
    @InjectMocks
    private ExpenseServiceImpl service;

    @Mock
    private AccountServiceImpl accountService;

    private Expense expense;
    private ExpenseDto expenseDto;

    private Long id;


    private Account account;

    private Category category;


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

        category = new Category();
        category.setId(id);
        category.setName("Test Name");
        category.setDescription("Test Description");

        AtomicReference<CategoryDto> categoryDto = new AtomicReference<>(new CategoryDto());
        categoryDto.get().setName("Test Name");
        categoryDto.get().setDescription("Test Description");


        expense = new Expense();
        expense.setId(id);
        expense.setDate(LocalDate.now());
        expense.setAmount(BigDecimal.valueOf(100));
        expense.setAccount(account);
        expense.setCategory(category);


        expenseDto = new ExpenseDto();
        expenseDto.setDate(LocalDate.now());
        expenseDto.setAmount(BigDecimal.valueOf(100));
        expenseDto.setAccountId(account.getId());
        expenseDto.setCategoryId(category.getId());
    }

    @Test
    void getAll() {
        List<Expense> entities = Collections.singletonList(expense);
        List<ExpenseDto> dtos = Collections.singletonList(expenseDto);

        when(repository.findAll()).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(dtos);

        var result = service.getAll();

        assertEquals(dtos, result);
        verify(repository).findAll();
        verify(mapper).toDto(entities);
    }

    @Test
    void getById() {
        when(repository.findById(id)).thenReturn(Optional.of(expense));
        when(mapper.toDto(expense)).thenReturn(expenseDto);
        var result = service.getById(id);
        assertEquals(expenseDto, result);

        verify(repository).findById(id);
        verify(mapper).toDto(expense);
    }

    @Test
    void getAllByAccountId() {
        List<Expense> entities = Collections.singletonList(expense);
        List<ExpenseDto> dtos = Collections.singletonList(expenseDto);

        when(repository.findByAccountId(id)).thenReturn(Optional.of(entities));
        when(mapper.toDto(entities)).thenReturn(dtos);

        var result = service.getAllByAccountId(id);

        assertEquals(dtos, result);
        verify(repository).findByAccountId(id);
        verify(mapper).toDto(entities);
    }

    @Test
    void add() {
        Expense mappedExpense = new Expense();
        mappedExpense.setAmount(BigDecimal.valueOf(150));
        mappedExpense.setCategory(category);
        mappedExpense.setAccount(account);
        mappedExpense.setDate(LocalDate.now());

        when(mapper.toEntity(expenseDto)).thenReturn(mappedExpense);
        when(repository.add(mappedExpense)).thenReturn(Optional.of(expense));
        expenseDto.setId(id);

        when(mapper.toDto(expense)).thenReturn(expenseDto);

        var result = service.add(expenseDto);

        assertEquals(expenseDto, result);
        verify(mapper).toEntity(expenseDto);
        verify(repository).add(mappedExpense);
        verify(mapper).toDto(expense);
    }

    @Test
    void whenNoIncome_thenThrowException() {
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getById(id));
    }


    @Test
    void updateById() {
        Expense updatedExpense = new Expense();
        updatedExpense.setId(id);
        updatedExpense.setAmount(BigDecimal.valueOf(199));
        updatedExpense.setDate(LocalDate.now());
        updatedExpense.setCategory(category);
        updatedExpense.setAccount(account);

        ExpenseDto updatedExpenseDto = new ExpenseDto();
        updatedExpenseDto.setAmount(BigDecimal.valueOf(199));
        updatedExpenseDto.setDate(LocalDate.now());
        updatedExpenseDto.setCategoryId(category.getId());
        updatedExpenseDto.setAccountId(account.getId());

        when(repository.findById(id))
                .thenReturn(Optional.of(expense));
        when(repository.updateById(id, mapper.toEntity(updatedExpenseDto)))
                .thenReturn(Optional.of(updatedExpense));
        when(mapper.toDto(updatedExpense))
                .thenReturn(updatedExpenseDto);

        var result = service.updateById(id, updatedExpenseDto);

        assertEquals(updatedExpenseDto, result);
        verify(repository).updateById(id, mapper.toEntity(updatedExpenseDto));
        verify(mapper).toDto(updatedExpense);

    }

    @Test
    void deleteById() {
        when(repository.findById(id)).thenReturn(Optional.of(expense));
        when(repository.deleteById(id)).thenReturn(Optional.of(id));

        service.deleteById(id);

        verify(repository).deleteById(id);
    }
}