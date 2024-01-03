package org.expensetracker.controller;

import org.expensetracker.service.ExpenseService;
import org.expensetracker.service.model.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense-tracker")
public class ExpenseController {
    private final ExpenseService service;

    @Autowired
    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseDto>> getAllExpenses() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/expenses")
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto addedExpense = service.add(expenseDto);
        return ResponseEntity
                .created(URI.create("/expense-tracker/expenses/" + addedExpense.getId()))
                .body(addedExpense);
    }

    @GetMapping("/expenses/account/{accountId}")
    public ResponseEntity<List<ExpenseDto>> getIncomesByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(service.getAllByAccountId(accountId));
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<ExpenseDto> updateExpenseById(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = service.updateById(id, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
