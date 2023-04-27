package services

import db.ExpenseFileRepository
import entities.Expense

class ExpenseService {
    private val expenseFileRepository = ExpenseFileRepository()
    fun getExpenses(): List<Expense> {
        return expenseFileRepository.getExpenses()
    }

    fun sortExpenses() {

    }
}