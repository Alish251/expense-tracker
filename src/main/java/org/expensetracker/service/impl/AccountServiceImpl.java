package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.repository.AccountRepository;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.mapper.AccountMapper;
import org.expensetracker.service.model.AccountDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;


    public AccountServiceImpl(AccountRepository repository, AccountMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;

    }

    @Override
    public List<AccountDto> getAll() {
        Optional<List<Account>> optionalAccounts = repository.findAll();
        List<Account> accounts = optionalAccounts
                .orElseThrow(() -> new RuntimeException("No accounts found"));
        return mapper.toDto(accounts);
    }

    @Override
    public AccountDto getById(Long id) {
        Optional<Account> optionalAccount = repository.findById(id);
        Account account = optionalAccount
                .orElseThrow(() -> new RuntimeException("No account found"));
        return mapper.toDto(account);
    }

    @Override
    public AccountDto add(AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }
        if (accountDto.getBalance() == null) {
            accountDto.setBalance(BigDecimal.valueOf(0));
        }
        Account account = mapper.toEntity(accountDto);
        Account savedAccount = repository.add(account)
                .orElseThrow(() -> new RuntimeException("Account not added"));
        return mapper.toDto(savedAccount);
    }

    @Override
    public void updateBalance(Long accountId, BigDecimal amount) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("No account found"));
        account.setBalance(account.getBalance().add(amount));

        repository.updateById(accountId, account);
    }

    @Override
    public AccountDto updateById(Long id, AccountDto accountDto) {
        if (accountDto == null) {
            return null;
        }

        Account account = repository.updateById(id, mapper.toEntity(accountDto))
                .orElseThrow(() -> new RuntimeException("Account not updated"));

        return mapper.toDto(account);
    }

    @Override
    public void deleteById(Long id) {
        Long optionalLong = repository.deleteById(id)
                .orElseThrow(() -> new RuntimeException("No user found"));
    }
}
