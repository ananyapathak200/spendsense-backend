package com.spendsense.backend.controller;

import com.spendsense.backend.dto.ExpenseRequest;
import com.spendsense.backend.dto.ExpenseResponse;
import com.spendsense.backend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // GET /expenses?userId=1
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAll(
            @RequestParam Long userId) {
        return ResponseEntity.ok(expenseService.getAllExpenses(userId));
    }

    // POST /expenses?userId=1
    @PostMapping
    public ResponseEntity<ExpenseResponse> create(
            @RequestParam Long userId,
            @RequestBody ExpenseRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseService.createExpense(userId, req));
    }

    // PUT /expenses/5?userId=1
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> update(
            @RequestParam Long userId,
            @PathVariable Long id,
            @RequestBody ExpenseRequest req) {
        return ResponseEntity.ok(
                expenseService.updateExpense(userId, id, req)
        );
    }

    // DELETE /expenses/5?userId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestParam Long userId,
            @PathVariable Long id) {
        expenseService.deleteExpense(userId, id);
        return ResponseEntity.noContent().build();
    }

    // GET /expenses/summary?userId=1
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Double>> summary(
            @RequestParam Long userId) {
        return ResponseEntity.ok(
                expenseService.getCategorySummary(userId)
        );
    }
}
