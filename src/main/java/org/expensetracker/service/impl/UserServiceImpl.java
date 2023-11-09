package org.expensetracker.service.impl;

import org.expensetracker.database.entity.User;
import org.expensetracker.database.repository.UserRepository;
import org.expensetracker.service.UserService;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getAll() {
        Optional<List<User>> optionalUsers = repository.findAll();
        List<User> users = optionalUsers.orElseThrow(() -> new RuntimeException("No users found"));
        return mapper.toDto(users);
    }

    @Override
    public UserDto getById(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("No user found"));
        return mapper.toDto(user);
    }

    @Override
    public UserDto add(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        User savedUser = repository.add(user).orElseThrow(() -> new RuntimeException("User not added"));
        return mapper.toDto(savedUser);
    }

    @Override
    public UserDto updateById(Long id, UserDto userDto) {
        User user = repository.updateById(id, mapper.toEntity(userDto)).orElseThrow(() -> new RuntimeException("User not updated"));

        return mapper.toDto(user);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id).orElseThrow(() -> new RuntimeException("No user found"));
    }
}
