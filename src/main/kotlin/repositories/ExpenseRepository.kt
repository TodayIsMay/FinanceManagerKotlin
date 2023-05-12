package repositories

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime

class ExpenseRepository(val jdbcTemplate: JdbcTemplate) {
    fun getExpenses():List<Expense> {
        return listOf()
    }

    fun insertExpense(expense: Expense) {
        jdbcTemplate.update("INSERT INTO expenses (comment, amount, category_id, expense_timestamp) VALUES (?, ?, ?, ?)", expense.comment, expense.amount, expense.categoryId, LocalDateTime.now())
    }
}