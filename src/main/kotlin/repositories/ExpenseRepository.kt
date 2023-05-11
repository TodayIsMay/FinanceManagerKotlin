package repositories

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.IllegalArgumentException

class ExpenseRepository(val jdbcTemplate: JdbcTemplate) {
    fun getExpenses():List<Expense> {
        return listOf()
    }

    fun insertExpense() {
        val sql: String = "INSERT INTO expenses (comment, amount, category_id) VALUES ('description', 12.1, 1)"//TODO: make it normal
        val dataSource = jdbcTemplate.dataSource
        if (dataSource == null) {
            println("DataSource is null!")
            throw IllegalArgumentException("DataSource is null!")
        }
        val connection = dataSource.connection
        if (connection == null) {
            println("DataSource is null!")
            throw IllegalArgumentException("DataSource is null!")
        }
        connection.createStatement().execute(sql)
        //jdbcTemplate.dataSource?.connection?.createStatement()?.execute(sql)
    }

}