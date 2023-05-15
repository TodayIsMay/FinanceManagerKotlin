package repositories

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class ExpenseRepository(private val jdbcTemplate: JdbcTemplate) {
    fun getExpenses(): List<Expense> {
        val sql = "SELECT * FROM expenses ORDER BY expense_timestamp DESC"
        val list = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Expense(
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getInt("category_id"),
                rs.getTimestamp("expense_timestamp").toLocalDateTime()
            )
        }
        return list
    }

    fun insertExpense(expense: Expense): Expense {
        jdbcTemplate.update(
            "INSERT INTO expenses (comment, amount, category_id, expense_timestamp) VALUES (?, ?, ?, ?)",
            expense.comment,
            expense.amount,
            expense.categoryId,
            expense.date
        )
        return expense;
    }
}