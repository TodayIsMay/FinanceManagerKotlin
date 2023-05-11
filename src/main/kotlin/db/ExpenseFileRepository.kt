package db

import ExpenseMatcher
import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import java.io.File
import java.time.LocalDateTime

class ExpenseFileRepository(private val jdbcTemplate: JdbcTemplate) {
    private val expenseMather = ExpenseMatcher()
    private val file: File = File("src/main/resources/Database")

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
     * Inserts expense into DB
     */
    fun insert(expense: Expense) {
        val sql = "insert into person (name, age, email) values ('name', 1, 'email')"
        val con = jdbcTemplate.dataSource?.connection
        con?.prepareStatement(sql)?.execute()
    }
}