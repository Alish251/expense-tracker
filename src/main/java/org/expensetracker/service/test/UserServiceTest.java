package org.expensetracker.service.test;

import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.database.repository.impl.UserRepositoryImpl;
import org.expensetracker.service.UserService;
import org.expensetracker.service.impl.UserServiceImpl;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.mapper.impl.UserMapperImpl;
import org.expensetracker.service.model.UserDto;

import java.util.HashSet;
import java.util.List;

public class UserServiceTest {
    public static void main(String[] args) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();


        UserRepository userRepository = new UserRepositoryImpl(configuration.buildSessionFactory());

        UserMapper userMapper = new UserMapperImpl();

        UserService userService = new UserServiceImpl(userRepository, userMapper);

        UserDto userDto = new UserDto();
        userDto.setFirstname("Test 2");
        userDto.setLastname("Test 2");
        userDto.setEmail("Test 2");
        userDto.setAccounts(new HashSet<>());

        //add - works
        //System.out.println(userService.add(userDto));

        //getAll - works
        List<UserDto> userDtoList = userService.getAll();
        System.out.println(userDtoList);

        //getById - doesn't work properly
//        UserDto userDto1 = userService.getById(1L);
//        System.out.println(userDto1.getAccounts());

        //updateById - works
//        UserDto userDtoUpdated = new UserDto();
//        userDtoUpdated.setFirstname("Updated");
//        userDtoUpdated.setLastname("Updated");
//        userDtoUpdated.setEmail("Updated");
//
//
//        System.out.println(userService.updateById(3L, userDtoUpdated));

        //userService.updateById(3L, userDtoUpdated);
        //delete
        //userService.deleteById(3L);

        //System.out.println(userService.getById(1L).getAccounts());
        // AccountDto accountDto1 = new AccountDto(BigDecimal.valueOf(500000), userDto);
        //AccountDto accountDto2 = new AccountDto(BigDecimal.valueOf(700000), userDto);

//        Set<AccountDto> accountDtoSet = new HashSet<>();
//        accountDtoSet.add(accountDto1);
//        accountDtoSet.add(accountDto2);
//        System.out.println(accountDtoSet);

//        userDto.setAccounts(accountDtoSet);
        //System.out.println(userMapper.toEntity(userDto).getAccounts());
//        System.out.println(userService.updateById(4L, userDto));
        //List<User> userList = userRepository.findAll().get();
        //List<UserDto> userDtoList = userMapper.toDto(userList);

        //System.out.println(userList);
        //System.out.println(userDtoList);
        //System.out.println(userDtoList.get(0).getAccounts());
        //System.out.println(userDtoList.get(1).getAccounts());
    }
}
