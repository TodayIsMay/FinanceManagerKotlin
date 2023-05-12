package services

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import repositories.ExpenseRepository

class ExpenseService(val jdbcTemplate: JdbcTemplate) {
    val expenseRepository: ExpenseRepository = ExpenseRepository(jdbcTemplate)
    fun getExpenses(): List<Expense> {
        return expenseRepository.getExpenses()
    }

    fun sortExpenses() {

    }

    fun insertExpense(expense: Expense): Expense {
        return expenseRepository.insertExpense(expense)
    }
}