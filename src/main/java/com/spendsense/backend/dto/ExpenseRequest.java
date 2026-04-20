package com.spendsense.backend.dto;

import java.time.LocalDate;

public class ExpenseRequest {
    private Double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Double getAmount()      { return amount; }
    public String getCategory()    { return category; }
    public LocalDate getDate()     { return date; }
    public String getDescription() { return description; }

    public void setAmount(Double amount)      { this.amount = amount; }
    public void setCategory(String category)  { this.category = category; }
    public void setDate(LocalDate date)       { this.date = date; }
    public void setDescription(String desc)   { this.description = desc; }
}
