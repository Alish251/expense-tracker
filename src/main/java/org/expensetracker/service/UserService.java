package org.expensetracker.service;

import jakarta.validation.constraints.NotNull;
import org.expensetracker.service.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(@NotNull Long id);

    UserDto add(@NotNull UserDto userDto);

    UserDto updateById(@NotNull Long id, @NotNull UserDto userDto);

    void deleteById(@NotNull Long id);
}
