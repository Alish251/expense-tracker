package org.expensetracker.service.impl;

import org.expensetracker.database.entity.*;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.IncomeRepository;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.database.repository.impl.AccountRepositoryImpl;
import org.expensetracker.database.repository.impl.IncomeRepositoryImpl;
import org.expensetracker.database.repository.impl.IncomeSourceRepositoryImpl;
import org.expensetracker.database.repository.impl.UserRepositoryImpl;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.IncomeService;
import org.expensetracker.service.IncomeSourceService;
import org.expensetracker.service.UserService;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.mapper.IncomeMapper;
import org.expensetracker.service.mapper.IncomeSourceMapper;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.mapper.impl.AccountMapperImpl;
import org.expensetracker.service.mapper.impl.IncomeMapperImpl;
import org.expensetracker.service.mapper.impl.IncomeSourceMapperImpl;
import org.expensetracker.service.mapper.impl.UserMapperImpl;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.IncomeDto;
import org.expensetracker.service.model.IncomeSourceDto;
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
public class IncomeServiceIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres");

    private static UserService userService;

    private static AccountService accountService;

    private static IncomeSourceService incomeSourceService;

    private static IncomeService incomeService;

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

        IncomeSourceRepository incomeSourceRepository = new IncomeSourceRepositoryImpl(sessionFactory);
        IncomeSourceMapper incomeSourceMapper = new IncomeSourceMapperImpl();
        incomeSourceService = new IncomeSourceServiceImpl(incomeSourceRepository, incomeSourceMapper);


        AccountRepository accountRepository = new AccountRepositoryImpl(sessionFactory);
        AccountMapper accountMapper = new AccountMapperImpl();
        accountService = new AccountServiceImpl(accountRepository, accountMapper);

        IncomeRepository incomeRepository = new IncomeRepositoryImpl(sessionFactory);
        IncomeMapper incomeMapper = new IncomeMapperImpl();
        incomeService = new IncomeServiceImpl(incomeRepository, incomeMapper, accountService);
    }

    @ParameterizedTest
    @CsvSource({
            "200.00, 150.00, 350.00",
            "100000.00, 50000.00, 150000.00",
            "12.28, 25.82, 38.10",
            "7777778.28, 545454.68, 8323232.96"
    })
    public void testBalanceAfterAddAndDeleteIncome(BigDecimal initialBalance, BigDecimal incomeAmount, BigDecimal expectedBalance) {
        UserDto user = getUserDto();
        user = userService.add(user);

        AccountDto account = getAccountDto(initialBalance, user);
        account = accountService.add(account);

        IncomeSourceDto incomeSourceDto = getIncomeSourceDto();
        incomeSourceDto = incomeSourceService.add(incomeSourceDto);

        IncomeDto income = getIncomeDto(incomeAmount, account, incomeSourceDto);

        //add test

        income = incomeService.add(income);
        account = accountService.getById(account.getId());
        assertEquals(expectedBalance, account.getBalance());

        //delete test

        incomeService.deleteById(income.getId());
        account = accountService.getById(account.getId());
        assertEquals(initialBalance, account.getBalance());
    }

    @ParameterizedTest
    @CsvSource({
            "1000.00, 1000.00, 5000.00",
            "100000.00, 50000.00, 150000.00",
            "12.28, 25.82, 38.10",
            "7777778.28, 545454.68, 8323232.96"
    })
    public void testBalanceAfterUpdateIncome(BigDecimal initialBalance, BigDecimal incomeAmount, BigDecimal updatedIncomeAmount) {
        UserDto user = getUserDto();
        user = userService.add(user);

        AccountDto account = getAccountDto(initialBalance, user);
        account = accountService.add(account);

        IncomeSourceDto incomeSourceDto = getIncomeSourceDto();
        incomeSourceDto = incomeSourceService.add(incomeSourceDto);

        IncomeDto income = getIncomeDto(incomeAmount, account, incomeSourceDto);

        //add test

        income = incomeService.add(income);

        //update income amount
        BigDecimal oldAccountBalance = accountService.getById(account.getId()).getBalance();

        IncomeDto newIncome = getIncomeDto(updatedIncomeAmount, account, incomeSourceDto);
        newIncome = incomeService.updateById(income.getId(), newIncome);

        BigDecimal newAccountBalance = accountService.getById(account.getId()).getBalance();

        BigDecimal accountBalanceDifference = newAccountBalance.subtract(oldAccountBalance);
        //Assertion

        BigDecimal incomesAmountDifference = newIncome.getAmount().subtract(income.getAmount());

        assertEquals(incomesAmountDifference, accountBalanceDifference);
    }

    @NotNull
    private IncomeDto getIncomeDto(BigDecimal incomeAmount, AccountDto account, IncomeSourceDto incomeSourceDto) {
        IncomeDto income = new IncomeDto();
        income.setAmount(incomeAmount);
        income.setAccountId(account.getId());
        income.setDate(LocalDate.now());
        income.setIncomeSourceId(incomeSourceDto.getId());
        return income;
    }

    @NotNull
    private IncomeSourceDto getIncomeSourceDto() {
        IncomeSourceDto incomeSourceDto = new IncomeSourceDto();
        incomeSourceDto.setName("Test name");
        incomeSourceDto.setDescription("Test description");
        return incomeSourceDto;
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
