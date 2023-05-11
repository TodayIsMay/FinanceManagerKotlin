package main

import db.DataSource
import db.ExpenseFileRepository
import entities.Expense
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import repositories.ExpenseRepository
import services.ExpenseService
import java.time.LocalDateTime

@RestController
@RequestMapping
class ExpenseController {
    private final val dataSource: DataSource = DataSource
    private final val jdbcTemplate: JdbcTemplate = JdbcTemplate(dataSource.createDataSource())
    private val expenseFileDAO: ExpenseFileRepository = ExpenseFileRepository(jdbcTemplate)
    private val expenseService: ExpenseService = ExpenseService(jdbcTemplate)

    @GetMapping("/expenses")
    fun getExpenses(): List<Expense> {
        return expenseFileDAO.getExpenses()
    }

    @GetMapping("/expenses/time")
    fun getExpensesInRange(@RequestParam(required = true) from: LocalDateTime,
                           @RequestParam(required = true) to: LocalDateTime): List<Expense> {
        return listOf(Expense(12.0, "comment", LocalDateTime.now()))
    }

    @GetMapping("/insert")
    fun insert() {
        expenseService.insertExpense()
    }
}