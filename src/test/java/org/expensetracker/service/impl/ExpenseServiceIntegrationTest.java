package org.expensetracker.service.impl;

import org.expensetracker.database.entity.*;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.CategoryRepository;
import org.expensetracker.database.repository.ExpenseRepository;
import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.database.repository.impl.AccountRepositoryImpl;
import org.expensetracker.database.repository.impl.CategoryRepositoryImpl;
import org.expensetracker.database.repository.impl.ExpenseRepositoryImpl;
import org.expensetracker.database.repository.impl.UserRepositoryImpl;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.CategoryService;
import org.expensetracker.service.ExpenseService;
import org.expensetracker.service.UserService;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.mapper.CategoryMapper;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.mapper.impl.AccountMapperImpl;
import org.expensetracker.service.mapper.impl.CategoryMapperImpl;
import org.expensetracker.service.mapper.impl.ExpenseMapperImpl;
import org.expensetracker.service.mapper.impl.UserMapperImpl;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.CategoryDto;
import org.expensetracker.service.model.ExpenseDto;
import org.expensetracker.service.model.UserDto;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class ExpenseServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres");

    private static UserService userService;

    private static AccountService accountService;

    private static CategoryService categoryService;

    private static ExpenseService expenseService;

    @BeforeAll
    public static void innit() {
        POSTGRE_SQL_CONTAINER.start();
        Properties properties = new Properties();

        properties.put("hibernate.connection.url", POSTGRE_SQL_CONTAINER.getJdbcUrl());
        properties.put("hibernate.connection.driver_class", POSTGRE_SQL_CONTAINER.getDriverClassName());
        properties.put("hibernate.connection.username", POSTGRE_SQL_CONTAINER.getUsername());
        properties.put("hibernate.connection.password", POSTGRE_SQL_CONTAINER.getPassword());
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgresPlusDialect");


        Configuration configuration = new Configuration();

        configuration.addProperties(properties);

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Income.class);
        configuration.addAnnotatedClass(IncomeSource.class);
        configuration.addAnnotatedClass(Expense.class);
        configuration.addAnnotatedClass(Category.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        UserRepository userRepository = new UserRepositoryImpl(sessionFactory);
        UserMapper userMapper = new UserMapperImpl();
        userService = new UserServiceImpl(userRepository, userMapper, accountService);

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(sessionFactory);
        CategoryMapper categoryMapper = new CategoryMapperImpl();
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);


        AccountRepository accountRepository = new AccountRepositoryImpl(sessionFactory);
        AccountMapper accountMapper = new AccountMapperImpl();
        accountService = new AccountServiceImpl(accountRepository, accountMapper);

        ExpenseRepository expenseRepository = new ExpenseRepositoryImpl(sessionFactory);
        ExpenseMapper expenseMapper = new ExpenseMapperImpl();
        expenseService = new ExpenseServiceImpl(expenseRepository, expenseMapper, accountService);
    }

    @ParameterizedTest
    @CsvSource({
            "1000.00, 500.00, 500.00",
            "100000.00, 50000.00, 50000.00",
            "12.28, 25.82, -13.54",
            "7777778.28, 545454.68, 7232323.60"
    })
    public void testBalanceAfterAddAndDeleteExpense(BigDecimal initialBalance, BigDecimal expenseAmount, BigDecimal expectedBalance) {
        UserDto user = getUserDto();
        user = userService.add(user);

        AccountDto account = getAccountDto(initialBalance, user);
        account = accountService.add(account);

        CategoryDto categoryDto = getCategoryDto();
        categoryDto = categoryService.add(categoryDto);

        ExpenseDto expenseDto = getExpenseDto(expenseAmount, account, categoryDto);

        //add test

        expenseDto = expenseService.add(expenseDto);
        account = accountService.getById(account.getId());
        assertEquals(expectedBalance, account.getBalance());

        //delete test

        expenseService.deleteById(expenseDto.getId());
        account = accountService.getById(account.getId());
        assertEquals(initialBalance, account.getBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "1000.00, 500.00, 800.00",
            "100000.00, 50000.00, 150000.00",
            "12.28, 25.82, 38.10",
            "7777778.28, 545454.68, 8323232.96"
    })
    public void testBalanceAfterUpdateExpense(BigDecimal initialBalance, BigDecimal incomeAmount, BigDecimal updatedIncomeAmount) {
        UserDto user = getUserDto();
        user = userService.add(user);

        AccountDto account = getAccountDto(initialBalance, user);
        account = accountService.add(account);

        CategoryDto categoryDto = getCategoryDto();
        categoryDto = categoryService.add(categoryDto);

        ExpenseDto expenseDto = getExpenseDto(incomeAmount, account, categoryDto);

        //add test

        expenseDto = expenseService.add(expenseDto);

        //update expenseDto amount
        BigDecimal oldAccountBalance = accountService.getById(account.getId()).getBalance();

        ExpenseDto newExpenseDto = getExpenseDto(updatedIncomeAmount, account, categoryDto);
        newExpenseDto = expenseService.updateById(expenseDto.getId(), newExpenseDto);

        BigDecimal newAccountBalance = accountService.getById(account.getId()).getBalance();

        BigDecimal accountBalanceDifference = newAccountBalance.subtract(oldAccountBalance);
        //Assertion

        BigDecimal expensesAmountDifference = newExpenseDto.getAmount().subtract(expenseDto.getAmount());

        assertEquals(expensesAmountDifference, accountBalanceDifference);
    }

    @NotNull
    private ExpenseDto getExpenseDto(BigDecimal incomeAmount, AccountDto account, CategoryDto categoryDto) {
        ExpenseDto income = new ExpenseDto();
        income.setAmount(incomeAmount);
        income.setAccountId(account.getId());
        income.setDate(LocalDate.now());
        income.setCategoryId(categoryDto.getId());
        return income;
    }

    @NotNull
    private CategoryDto getCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Test name");
        categoryDto.setDescription("Test description");
        return categoryDto;
    }

    @NotNull
    private AccountDto getAccountDto(BigDecimal initialBalance, UserDto user) {
        AccountDto account = new AccountDto();
        account.setBalance(initialBalance);
        account.setUser(user);
        return account;
    }

    @NotNull
    private UserDto getUserDto() {
        UserDto user = new UserDto();
        user.setFirstname("Test name");
        user.setLastname("Test lastname");
        user.setEmail("Test email");
        return user;
    }

    @AfterAll
    public static void shutDown() {
        POSTGRE_SQL_CONTAINER.close();
    }
}
