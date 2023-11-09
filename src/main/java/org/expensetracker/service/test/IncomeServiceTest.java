package org.expensetracker.service.test;

import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.IncomeRepository;
import org.expensetracker.database.repository.IncomeSourceRepository;
import org.expensetracker.database.repository.impl.AccountRepositoryImpl;
import org.expensetracker.database.repository.impl.IncomeRepositoryImpl;
import org.expensetracker.database.repository.impl.IncomeSourceRepositoryImpl;
import org.expensetracker.service.IncomeService;
import org.expensetracker.service.impl.IncomeServiceImpl;
import org.expensetracker.service.mapper.IncomeMapper;
import org.expensetracker.service.mapper.impl.IncomeMapperImpl;
import org.expensetracker.service.model.IncomeDto;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

public class IncomeServiceTest {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();

        AccountRepository accountRepository = new AccountRepositoryImpl(configuration.buildSessionFactory());

        IncomeSourceRepository incomeSourceRepository = new IncomeSourceRepositoryImpl(configuration.buildSessionFactory());

        IncomeRepository incomeRepository = new IncomeRepositoryImpl(configuration.buildSessionFactory());

        IncomeMapper incomeMapper = new IncomeMapperImpl(accountRepository, incomeSourceRepository);

        IncomeService incomeService = new IncomeServiceImpl(incomeRepository, incomeMapper);

        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setAmount(BigDecimal.valueOf(80));
        incomeDto.setDate(LocalDate.now());
        incomeDto.setAccountId(1L);
        incomeDto.setIncomeSourceId(1L);

        //getById - works
        //System.out.println(incomeService.getById(1L));
        //getAll - works
        //System.out.println(incomeService.getAll());
        //add - works
        //System.out.println(incomeService.add(incomeDto));
        //updateById - works
        //System.out.println(incomeService.updateById(3L, incomeDto));
        //deleteById - works
        //incomeService.deleteById(3L);

    }
}
