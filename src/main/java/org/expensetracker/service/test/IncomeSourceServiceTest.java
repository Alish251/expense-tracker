package org.expensetracker.service.test;

import org.expensetracker.database.repository.IncomeSourceRepository;
import org.expensetracker.database.repository.impl.IncomeSourceRepositoryImpl;
import org.expensetracker.service.IncomeSourceService;
import org.expensetracker.service.impl.IncomeSourceServiceImpl;
import org.expensetracker.service.mapper.IncomeSourceMapper;
import org.expensetracker.service.mapper.impl.IncomeSourceMapperImpl;
import org.expensetracker.service.model.IncomeSourceDto;

public class IncomeSourceServiceTest {
    public static void main(String[] args) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();

        IncomeSourceRepository incomeSourceRepository = new IncomeSourceRepositoryImpl(configuration.buildSessionFactory());

        IncomeSourceMapper incomeSourceMapper = new IncomeSourceMapperImpl();

        IncomeSourceService incomeSourceService = new IncomeSourceServiceImpl(incomeSourceRepository, incomeSourceMapper);


        IncomeSourceDto incomeSourceDto = new IncomeSourceDto();
        incomeSourceDto.setName("Dto name");
        incomeSourceDto.setDescription("Dto description");


        //getById - works
        //System.out.println(incomeSourceService.getById(1L));
        //getAll - works
        //System.out.println(incomeSourceService.getAll());
        //add - works
        //System.out.println(incomeSourceService.add(incomeSourceDto));
        //updateById - works
        //System.out.println(incomeSourceService.updateById(2L, incomeSourceDto));
        //deleteById - works
        incomeSourceService.deleteById(3L);
    }
}
