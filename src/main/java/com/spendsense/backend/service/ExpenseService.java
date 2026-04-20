package com.spendsense.backend.service;

import com.spendsense.backend.dto.ExpenseRequest;
import com.spendsense.backend.dto.ExpenseResponse;
import com.spendsense.backend.model.Expense;
import com.spendsense.backend.model.User;
import com.spendsense.backend.repository.ExpenseRepository;
import com.spendsense.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    // Expense object ko ExpenseResponse mein convert karna
    private ExpenseResponse toResponse(Expense e) {
        ExpenseResponse res = new ExpenseResponse();
        res.setId(e.getId());
        res.setAmount(e.getAmount());
        res.setCategory(e.getCategory());
        res.setDate(e.getDate());
        res.setDescription(e.getDescription());
        res.setCreatedAt(e.getCreatedAt());
        return res;
    }

    // Sirf us user ki saari expenses
    public List<ExpenseResponse> getAllExpenses(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Nayi expense save karo
    public ExpenseResponse createExpense(Long userId, ExpenseRequest req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(req.getAmount());
        expense.setCategory(req.getCategory());
        expense.setDate(req.getDate());
        expense.setDescription(req.getDescription());
        expense.setUser(user);

        return toResponse(expenseRepository.save(expense));
    }

    // Expense update karo
    public ExpenseResponse updateExpense(Long userId, Long expenseId,
                                         ExpenseRequest req) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Security — sirf apni expense update kar sakta hai
        if (!expense.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }

        expense.setAmount(req.getAmount());
        expense.setCategory(req.getCategory());
        expense.setDate(req.getDate());
        expense.setDescription(req.getDescription());

        return toResponse(expenseRepository.save(expense));
    }

    // Expense delete karo
    public boolean deleteExpense(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Security — sirf apni expense delete kar sakta hai
        if (!expense.getUser().getId().equals(userId)) {
            throw new RuntimeException("Not authorized");
        }

        expenseRepository.deleteById(expenseId);
        return true;
    }

    // Category summary for pie chart
    public Map<String, Double> getCategorySummary(Long userId) {
        List<Object[]> results = expenseRepository
                .findCategorySummaryByUser(userId);

        Map<String, Double> summary = new HashMap<>();
        for (Object[] row : results) {
            summary.put((String) row[0], ((Number) row[1]).doubleValue());
        }
        return summary;
    }
}
