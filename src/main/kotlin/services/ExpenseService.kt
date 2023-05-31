package services

import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import repositories.ExpenseRepository
import java.util.logging.Logger

class ExpenseService(jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val expenseRepository: ExpenseRepository = ExpenseRepository(jdbcTemplate)

    fun getExpenses(): List<Expense> {
        log.info("Trying to get expenses...")
        return expenseRepository.getExpenses()
    }

    fun getLastInsertedExpense(): Expense {
        log.info("Trying to get last inserted expense...")
        return expenseRepository.getLastInsertedExpense()
    }

    fun sortExpenses() {

    }

    fun insertExpense(expense: Expense): Expense {
        log.info("Inserting expense $expense")
        return expenseRepository.insertExpense(expense)
    }
}