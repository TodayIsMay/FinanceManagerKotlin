package repositories

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate

class ExpenseRepository(val jdbcTemplate: JdbcTemplate) {
    fun getExpenses():List<Expense> {
        return listOf()
    }

    fun insertExpense(expense: Expense): Expense {
        jdbcTemplate.update("INSERT INTO expenses (comment, amount, category_id, expense_timestamp) VALUES (?, ?, ?, ?)", expense.comment, expense.amount, expense.categoryId, expense.date)
        return expense;
    }
}