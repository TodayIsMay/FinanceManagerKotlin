package main

import db.ExpenseFileRepository
import entities.Expense
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.ExpenseService
import java.time.LocalDateTime

@RestController
@RequestMapping
class ExpenseController(
    @Autowired
    val managerBeans: ManagerBeans
) {
    private val expenseFileDAO: ExpenseFileRepository = ExpenseFileRepository(managerBeans.jdbcTemplate())
    private val expenseService: ExpenseService = managerBeans.expenseService()

    @GetMapping("/version")
    fun getVersion(): String {
        return "21.05.2023 20:33"
    }

    @GetMapping("/expenses")
    fun getExpenses(): List<Expense> {
        return expenseService.getExpenses()
    }

    @GetMapping("/expenses/time")
    fun getExpensesInRange(@RequestParam(required = true) from: LocalDateTime,
                           @RequestParam(required = true) to: LocalDateTime): List<Expense> {
        return listOf(Expense(12.0, "comment", 1, LocalDateTime.now()))
    }

    @PostMapping("/insert")
    fun insert(@RequestBody expense: Expense): Expense {
        return expenseService.insertExpense(expense)
    }
}