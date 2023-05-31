package repositories

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class ExpenseRepository(private val jdbcTemplate: JdbcTemplate) {
    fun getExpenses(): List<Expense> {
        val sql = "SELECT * FROM expenses ORDER BY expense_timestamp DESC"
        val list = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Expense(
                rs.getLong("id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("expense_timestamp").toLocalDateTime()
            )
        }
        return list
    }

    fun getLastInsertedExpense(): Expense {
        val sql = "SELECT * FROM expenses ORDER BY creation_timestamp DESC LIMIT 1"
        val expense = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Expense(
                rs.getLong("id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("expense_timestamp").toLocalDateTime()
            )
        }[0]
        return expense
    }

    fun insertExpense(expense: Expense): Expense {
        jdbcTemplate.update(
            "INSERT INTO expenses (comment, amount, category_id, expense_timestamp, creation_timestamp) VALUES (?, ?, ?, ?, ?)",
            expense.comment,
            expense.amount,
            expense.categoryId,
            expense.date,
            expense.creationTimestamp
        )
        return expense;
    }
}