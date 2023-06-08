package repositories

import entities.Expense
import entities.Income
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class IncomeRepository(private val jdbcTemplate: JdbcTemplate) {
    fun getTransactionsByUserId(id: Long): List<Income> {
        val sql = "SELECT * FROM incomes WHERE user_id = $id ORDER BY income_timestamp DESC"
        val list = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Income(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("income_timestamp").toLocalDateTime()
            )
        }
        return list
    }

    fun insertTransaction(income: Income) {
        val sql = "INSERT INTO incomes (comment, amount, category_id, income_timestamp, creation_timestamp, user_id) VALUES (?, ?, ?, ?, ?, ?)"
        jdbcTemplate.update(sql,
        income.comment,
        income.amount,
        income.categoryId,
        income.date,
        income.creationTimestamp,
        income.userId)
    }

    fun getLastInsertedExpenseByUserId(id: Long): Income {
        val sql = "SELECT * FROM incomes WHERE user_id = $id ORDER BY income_timestamp DESC LIMIT 1"
        return jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Income(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("income_timestamp").toLocalDateTime()
            )
        }[0]
    }
}