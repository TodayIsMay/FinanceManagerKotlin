import db.ExpenseFileDAO
import entities.Expense
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDateTime

fun main(args: Array<String>) {
    println("Hello World!")
    val expenses = listOf(Expense(12.0,null, LocalDateTime.now()),
        Expense(13.0, "comment", LocalDateTime.now()))
    println("List of expenses: $expenses")
    val fileDao: ExpenseFileDAO = ExpenseFileDAO()
    fileDao.insert(Expense(14.0, "comment", LocalDateTime.now()))
    val from = LocalDateTime.of(2023, 4, 23, 0, 0, 0)
    val to = LocalDateTime.of(2023, 5, 23, 0, 0, 0)
    val result = fileDao.getExpenses(from, to)
    println("Result: $result")
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}