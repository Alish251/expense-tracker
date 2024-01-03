package org.expensetracker.service.impl;

import org.expensetracker.database.entity.Account;
import org.expensetracker.database.entity.Category;
import org.expensetracker.database.entity.Expense;
import org.expensetracker.database.repository.ExpenseRepository;
import org.expensetracker.service.AccountService;
import org.expensetracker.service.ExpenseService;
import org.expensetracker.service.mapper.ExpenseMapper;
import org.expensetracker.service.model.ExpenseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository repository;
    private final ExpenseMapper mapper;

    private final AccountService accountService;

    public ExpenseServiceImpl(ExpenseRepository repository, ExpenseMapper mapper, AccountService accountService) {
        this.repository = repository;
        this.mapper = mapper;
        this.accountService = accountService;
    }

    @Override
    public List<ExpenseDto> getAll() {
        List<Expense> expenseList = repository.findAll();
//        List<Expense> expenses = optionalUsers
//                .orElseThrow(() -> new RuntimeException("No expenses found"));
        return mapper.toDto(expenseList);
    }

    @Override
    public ExpenseDto getById(Long id) {
        Optional<Expense> optionalUser = repository.findById(id);
        Expense expense = optionalUser
                .orElseThrow(() -> new RuntimeException("No expense found"));
        return mapper.toDto(expense);
    }

    @Override
    public List<ExpenseDto> getAllByAccountId(Long id) {
        List<Expense> allByAccountId = repository.getAllByAccount_Id(id);
//        List<Expense> expenseList = optionalExpenseList
//                .orElseThrow(() -> new RuntimeException("No incomes found for selected account"));
        return mapper.toDto(allByAccountId);
    }

    @Override
    public ExpenseDto add(ExpenseDto expenseDto) {
        if (expenseDto == null) {
            return null;
        }
        if (expenseDto.getDate() == null) {
            expenseDto.setDate(LocalDate.now());
        }
        Expense expense = mapper.toEntity(expenseDto);
        Expense savedExpense = repository.save(expense);

        accountService.updateBalance(expenseDto.getAccountId(), expense.getAmount().negate());

        return mapper.toDto(savedExpense);
    }

    @Override
    public ExpenseDto updateById(Long id, ExpenseDto expenseDto) {
        if (expenseDto == null) {
            return null;
        }

        Expense oldExpense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No expense found"));

        BigDecimal amountDifference = expenseDto.getAmount().subtract(oldExpense.getAmount());

        if (expenseDto.getAmount()!=null){
            oldExpense.setAmount(expenseDto.getAmount());
        }
        if (expenseDto.getCategoryId()!=null){
            oldExpense.setCategory(Category.builder().id(expenseDto.getCategoryId()).build());
        }
        if (expenseDto.getAccountId()!=null){
            oldExpense.setAccount(Account.builder().id(expenseDto.getAccountId()).build());
        }
        if (expenseDto.getDate() != null){
            oldExpense.setDate(expenseDto.getDate());
        }

        Expense newExpense = repository.save(oldExpense);


        accountService.updateBalance(newExpense.getAccount().getId(), amountDifference.negate());


        return mapper.toDto(newExpense);
    }

    @Override
    public void deleteById(Long id) {
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No expense found"));

        repository.deleteById(id);

        accountService.updateBalance(expense.getAccount().getId(), expense.getAmount());
    }
}
