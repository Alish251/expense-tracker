package org.expensetracker.service.test;

import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.CategoryRepository;
import org.expensetracker.database.repository.ExpenseRepository;
import org.expensetracker.database.repository.impl.AccountRepositoryImpl;
import org.expensetracker.database.repository.impl.CategoryRepositoryImpl;
import org.expensetracker.database.repository.impl.ExpenseRepositoryImpl;
import org.expensetracker.service.ExpenseService;
import org.expensetracker.service.impl.ExpenseServiceImpl;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.mapper.impl.ExpenseMapperImpl;
import org.expensetracker.service.model.ExpenseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseServiceTest {
    public static void main(String[] args) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();

        ExpenseRepository expenseRepository = new ExpenseRepositoryImpl(configuration.buildSessionFactory());

        AccountRepository accountRepository = new AccountRepositoryImpl(configuration.buildSessionFactory());

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(configuration.buildSessionFactory());

        ExpenseMapper expenseMapper = new ExpenseMapperImpl(accountRepository, categoryRepository);

        ExpenseService expenseService = new ExpenseServiceImpl(expenseRepository, expenseMapper);

        ExpenseDto expenseDto = new ExpenseDto();

        expenseDto.setAmount(BigDecimal.valueOf(777));
        expenseDto.setDate(LocalDate.now());
        expenseDto.setCategoryId(4L);
        expenseDto.setAccountId(2L);

        //getById - works
        //System.out.println(expenseService.getById(1L));
        //getAll - works
        //System.out.println(expenseService.getAll());
        //add - works
        //System.out.println(expenseService.add(expenseDto));
        //updateById - works
        System.out.println(expenseService.updateById(5L, expenseDto));
        //deleteById - works
        //expenseService.deleteById(3L);

    }
}
