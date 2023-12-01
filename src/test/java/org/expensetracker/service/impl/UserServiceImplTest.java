package org.expensetracker.service.impl;

import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.mapper.UserMapper;
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
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserDto userDto;
    private User user;
    private Long userId;

    @BeforeEach
    public void init() {
        userId = 1L;

        userDto = new UserDto();
        userDto.setFirstname("Test Name");
        userDto.setLastname("Test Lastname");
        userDto.setEmail("Test Email");

        user = new User();
        user.setId(userId);
        user.setFirstname("Test Name");
        user.setLastname("Test Lastname");
        user.setEmail("Test Email");
    }


    @Test
    void getAll() {
        var entities = Collections.singletonList(user);
        when(userRepository.findAll()).thenReturn(Optional.of(entities));
        when(userMapper.toDto(entities)).thenReturn(Collections.singletonList(userDto));

        var result = userServiceImpl.getAll();

        assertEquals(Collections.singletonList(userDto), result);

        verify(userRepository).findAll();
        verify(userMapper).toDto(entities);
    }

    @Test
    void getById() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        var result = userServiceImpl.getById(userId);
        assertEquals(userDto, result);

        verify(userRepository).findById(userId);
        verify(userMapper).toDto(user);

    }

    @Test
    void whenNoUser_thenThrowException() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userServiceImpl.getById(userId));
    }

    @Test
    void add() {

        User mappedUser = new User();
        mappedUser.setFirstname("Test Name");
        mappedUser.setLastname("Test Lastname");
        mappedUser.setEmail("Test Email");
        when(userMapper.toEntity(userDto)).thenReturn(mappedUser);
        when(userRepository.add(mappedUser)).thenReturn(Optional.of(user));
        userDto.setId(userId);
        when(userMapper.toDto(user)).thenReturn(userDto);
        AccountDto accountDto = new AccountDto();
        accountDto.setBalance(BigDecimal.valueOf(1500));
//        when(userDto.getAccounts()).thenReturn(Collections.singletonList(accountDto));
        accountDto.setId(userId);
        when(accountService.add(accountDto)).thenReturn(accountDto);
        userDto.setAccounts(Collections.singletonList(accountDto));

        var result = userServiceImpl.add(userDto);
        assertEquals(userDto, result);

        verify(userMapper).toDto(user);
        verify(userMapper).toEntity(userDto);
        verify(userRepository).add(mappedUser);
        verify(accountService).add(accountDto);
    }

    @Test
    void updateById() {

        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setFirstname("Updated Name");
        updatedUserDto.setLastname("Updated LastName");
        updatedUserDto.setEmail("Updated Email");

        User updatedUser = new User();
        updatedUser.setFirstname("Updated Name");
        updatedUser.setLastname("Updated LastName");
        updatedUser.setEmail("Updated Email");

        when(userRepository.updateById(userId, userMapper.toEntity(updatedUserDto)))
                .thenReturn(Optional.of(updatedUser));
        when(userMapper.toDto(updatedUser)).thenReturn(updatedUserDto);

        var result = userServiceImpl.updateById(userId, updatedUserDto);

        assertEquals(updatedUserDto, result);
        verify(userRepository).updateById(userId, userMapper.toEntity(updatedUserDto));
        verify(userMapper).toDto(updatedUser);
    }

    @Test
    void deleteById() {
        when(userRepository.deleteById(userId)).thenReturn(Optional.of(userId));

        userServiceImpl.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }
}