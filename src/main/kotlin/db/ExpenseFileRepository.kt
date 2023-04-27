package db

import ExpenseMatcher
import entities.Expense
import java.io.File
import java.time.LocalDateTime

class ExpenseFileRepository {
    private val expenseMather = ExpenseMatcher()
    private val file: File = File("src/main.main/resources/Database")

    fun getExpenses(): List<Expense> {
        val lines = file.readText().split(";")
        val expenses = ArrayList<Expense>()
        for (line in lines.withIndex()) {
            try {
                val expense = expenseMather.matchStringToExpense(line.value)
                expenses.add(expense)
            } catch (e: IllegalArgumentException){
                println(e.message)
            }
        }
        return expenses
    }
    /**
     * Find expenses in the given timerange inclusively
     */
    fun getExpensesInRange(from: LocalDateTime, to: LocalDateTime): Set<Expense> {
        val lines = file.readText().split(";")
        val expenses = ArrayList<Expense>()
        for (line in lines.withIndex()) {
            try {
                val expense = expenseMather.matchStringToExpense(line.value)
                expenses.add(expense)
            } catch (e: IllegalArgumentException){
                println(e.message)
            }
        }
        val result = HashSet<Expense>()
        for (expense in expenses) {
            if (expense.date.isAfter(from) && expense.date.isBefore(to)) {
                result.add(expense)
            }
        }
        return result
    }

    /**
     * Inserts expense into fileDB
     */
    fun insert(expense: Expense) {
        file.appendText("${expense.date},${expense.comment},${expense.amount};")
    }
}