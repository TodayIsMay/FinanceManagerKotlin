package db

import entities.Expense
import java.time.LocalDateTime

class ExpenseInMemoryDAO() {
    private val map: Map<LocalDateTime, Expense> = HashMap()
    fun findExpenseByDate(dateTime: LocalDateTime) {

    }
}