package com.spendsense.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ExpenseResponse {
    private Long id;
    private Double amount;
    private String category;
    private LocalDate date;
    private String description;
    private LocalDateTime createdAt;

    public Long getId()            { return id; }
    public Double getAmount()      { return amount; }
    public String getCategory()    { return category; }
    public LocalDate getDate()     { return date; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id)                { this.id = id; }
    public void setAmount(Double amount)      { this.amount = amount; }
    public void setCategory(String category)  { this.category = category; }
    public void setDate(LocalDate date)       { this.date = date; }
    public void setDescription(String desc)   { this.description = desc; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}
