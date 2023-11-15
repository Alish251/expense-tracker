package org.expensetracker.controller;

import org.expensetracker.service.IncomeSourceService;
import org.expensetracker.service.model.IncomeSourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense-tracker")
public class IncomeSourceController {
    private final IncomeSourceService service;

    @Autowired
    public IncomeSourceController(IncomeSourceService service) {
        this.service = service;
    }

    @GetMapping("/income_sources")
    public ResponseEntity<List<IncomeSourceDto>> getAllIncomeSources() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/income_sources/{id}")
    public ResponseEntity<IncomeSourceDto> getIncomeSourceById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/income_sources")
    public ResponseEntity<IncomeSourceDto> addIncome(@RequestBody IncomeSourceDto incomeSourceDto) {
        IncomeSourceDto addedIncomeSource = service.add(incomeSourceDto);
        return ResponseEntity
                .created(URI.create("/expense-tracker/income_sources/" + addedIncomeSource.getId()))
                .body(addedIncomeSource);
    }

    @PutMapping("/income_sources/{id}")
    public ResponseEntity<IncomeSourceDto> updateIncomeSourceById(@PathVariable Long id, @RequestBody IncomeSourceDto incomeSourceDto) {
        IncomeSourceDto updatedIncome = service.updateById(id, incomeSourceDto);
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/income_sources/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
