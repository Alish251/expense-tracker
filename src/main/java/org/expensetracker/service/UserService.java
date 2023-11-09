package org.expensetracker.service;

import org.expensetracker.service.model.UserDto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    UserDto getById(@NotNull Long id);

    UserDto add(@NotNull UserDto userDto);

    UserDto updateById(@NotNull Long id, @NotNull UserDto userDto);

    void deleteById(@NotNull Long id);
}
