package org.expensetracker.controller;

import org.expensetracker.service.IncomeService;
import org.expensetracker.service.model.IncomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense-tracker")
public class IncomeController {
    private final IncomeService service;

    @Autowired
    public IncomeController(IncomeService service) {
        this.service = service;
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDto>> getAllIncomes() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/incomes/{id}")
    public ResponseEntity<IncomeDto> getIncomeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/incomes/account/{accountId}")
    public ResponseEntity<List<IncomeDto>> getIncomesByAccountId(@PathVariable Long accountId) {
        return ResponseEntity.ok(service.getByAccountId(accountId));
    }

    @PostMapping("/incomes")
    public ResponseEntity<IncomeDto> addIncome(@RequestBody IncomeDto incomeDto) {
        IncomeDto addedIncome = service.add(incomeDto);
        return ResponseEntity
                .created(URI.create("/expense-tracker/incomes/" + addedIncome.getId()))
                .body(addedIncome);
    }

    @PutMapping("/incomes/{id}")
    public ResponseEntity<IncomeDto> updateIncomeById(@PathVariable Long id, @RequestBody IncomeDto incomeDto) {
        IncomeDto updatedIncome = service.updateById(id, incomeDto);
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
