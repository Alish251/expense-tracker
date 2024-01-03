package org.expensetracker.controller;

import org.expensetracker.service.CategoryService;
import org.expensetracker.service.model.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/expense-tracker")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto addedCategory = service.add(categoryDto);
        return ResponseEntity
                .created(URI.create("/expense-tracker/categories/" + addedCategory.getId()))
                .body(addedCategory);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateAccountById(@PathVariable Long id, @RequestBody CategoryDto accountDto) {
        CategoryDto updatedCategory = service.updateById(id, accountDto);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
