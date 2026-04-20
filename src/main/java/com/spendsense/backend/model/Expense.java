package com.spendsense.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDate date;

    private String description;

    // Har expense ek user ki hogi — user_id column banta hai
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public Long getId()            { return id; }
    public Double getAmount()      { return amount; }
    public String getCategory()    { return category; }
    public LocalDate getDate()     { return date; }
    public String getDescription() { return description; }
    public User getUser()          { return user; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id)                { this.id = id; }
    public void setAmount(Double amount)      { this.amount = amount; }
    public void setCategory(String category)  { this.category = category; }
    public void setDate(LocalDate date)       { this.date = date; }
    public void setDescription(String desc)   { this.description = desc; }
    public void setUser(User user)            { this.user = user; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}
