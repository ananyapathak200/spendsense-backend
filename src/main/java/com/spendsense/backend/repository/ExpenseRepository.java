package com.spendsense.backend.repository;

import com.spendsense.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Sirf us user ki expenses — yahi important hai
    List<Expense> findByUserId(Long userId);

    // Category summary for pie chart
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e " +
            "WHERE e.user.id = :userId " +
            "GROUP BY e.category")
    List<Object[]> findCategorySummaryByUser(@Param("userId") Long userId);
}
