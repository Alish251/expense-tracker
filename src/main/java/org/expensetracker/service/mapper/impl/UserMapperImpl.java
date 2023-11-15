package org.expensetracker.service.mapper.impl;

import org.expensetracker.database.entity.User;
import org.expensetracker.service.mapper.UserMapper;
import org.expensetracker.service.model.AccountDto;
import org.expensetracker.service.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class UserMapperImpl implements UserMapper {


    public UserMapperImpl() {
    }

    @Override
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        final UserDto userDto = new UserDto();
        userDto.setId(userDto.getId());
        userDto.setEmail(entity.getEmail());
        userDto.setFirstname(entity.getFirstname());
        userDto.setLastname(entity.getLastname());

        if (entity.getAccounts() != null && !entity.getAccounts().isEmpty()) {
//            Set<AccountDto> accountDtoSet = new HashSet<>();
//            for (Account account : entity.getAccounts()) {
//                AccountDto accountDto = new AccountDto();
//                accountDto.setBalance(account.getBalance());
//                accountDto.setUserDto(userDto); //ѕроверить работоспособность - работает!
//                accountDtoSet.add(accountDto);
//            }

            Set<AccountDto> accountDtoSet = entity.getAccounts().stream().map( //todo
                            element -> {
                                AccountDto accountDto = new AccountDto();
                                accountDto.setBalance(element.getBalance());
                                accountDto.setUserId(element.getUser().getId());
                                return accountDto;
                            })
                    .collect(Collectors.toSet());

            userDto.setAccounts(accountDtoSet);
        }

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        final User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());

        return user;
    }

    @Override
    public List<UserDto> toDto(List<User> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> toEntity(List<UserDto> userDtoList) {
        if (userDtoList == null) {
            return null;
        }
        return userDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
