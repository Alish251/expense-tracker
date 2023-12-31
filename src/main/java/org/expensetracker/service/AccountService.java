package org.expensetracker.service;

import org.expensetracker.service.model.AccountDto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<AccountDto> getAll();

    AccountDto getById(@NotNull Long id);

    AccountDto add(@NotNull AccountDto accountDto);

    void updateBalance(Long accountId, BigDecimal amount);

    AccountDto updateById(@NotNull Long id, @NotNull AccountDto accountDto);

    void deleteById(@NotNull Long id);
}
