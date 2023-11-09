package org.expensetracker.service.mapper;

import org.expensetracker.database.entity.User;
import org.expensetracker.service.model.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto toDto(User entity);

    User toEntity(UserDto userDto);

    List<UserDto> toDto(List<User> entities);

    List<User> toEntity(List<UserDto> userDtoList);
}
