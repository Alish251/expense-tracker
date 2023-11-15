package org.expensetracker.controller;

import org.expensetracker.service.AccountService;
import org.expensetracker.service.model.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense-tracker")
public class AccountController {
    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        AccountDto addedUser = service.add(accountDto);
        return ResponseEntity
                .created(URI.create("/expense-tracker/accounts/" + addedUser.getId()))
                .body(addedUser);
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> updateAccountById(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = service.updateById(id, accountDto);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
