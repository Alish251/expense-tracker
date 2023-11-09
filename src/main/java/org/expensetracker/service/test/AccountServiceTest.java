package org.expensetracker.service.test;

import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.database.repository.impl.AccountRepositoryImpl;
import org.expensetracker.database.repository.impl.UserRepositoryImpl;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.UserService;
import org.expensetracker.service.impl.AccountServiceImpl;
import org.expensetracker.service.impl.UserServiceImpl;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.mapper.impl.AccountMapperImpl;
import org.expensetracker.service.mapper.impl.UserMapperImpl;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.UserDto;

import java.math.BigDecimal;

public class AccountServiceTest {
    public static void main(String[] args) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();

        UserRepository userRepository = new UserRepositoryImpl(configuration.buildSessionFactory());

        UserMapper userMapper = new UserMapperImpl();

        UserService userService = new UserServiceImpl(userRepository, userMapper);

        UserDto userDto = userService.getById(2L);


        AccountRepository accountRepository = new AccountRepositoryImpl(configuration.buildSessionFactory());

        AccountMapper accountMapper = new AccountMapperImpl(userMapper, userRepository);

        AccountService accountService = new AccountServiceImpl(accountRepository, accountMapper);

        AccountDto accountDto = new AccountDto();


        accountDto.setBalance(BigDecimal.valueOf(2100));
        accountDto.setUserId(3L);

        //add - works
        //System.out.println(accountService.add(accountDto));

        //getAll - works
        //List<AccountDto> accountDtoList = accountService.getAll();
        //System.out.println(accountDtoList);

        //getById -works
        //AccountDto accountDto1 = accountService.getById(1L);
        //System.out.println(accountDto1);
        //updateById - doesn't work
        accountService.updateById(5L, accountDto);
        //delete - works

        //accountService.deleteById(8L);


    }
}
