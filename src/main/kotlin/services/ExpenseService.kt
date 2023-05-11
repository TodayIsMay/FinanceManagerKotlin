package services

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import repositories.ExpenseRepository
import java.time.LocalDateTime

class ExpenseService(val jdbcTemplate: JdbcTemplate) {
    val expenseRepository: ExpenseRepository = ExpenseRepository(jdbcTemplate)
    fun getExpenses(): List<Expense> {
        return expenseRepository.getExpenses()
    }

    fun sortExpenses() {

    }

    fun insertExpense() {
        expenseRepository.insertExpense()
    }
}