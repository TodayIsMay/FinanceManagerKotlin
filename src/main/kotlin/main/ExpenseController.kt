package main

import ExpenseMapper
import entities.Expense
import entities.dto.ExpenseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.ExpenseService

@RestController
@RequestMapping
class ExpenseController(
    @Autowired
    val managerBeans: ManagerBeans
) {
    private val expenseService: ExpenseService = managerBeans.expenseService()
    private val expenseMapper: ExpenseMapper = ExpenseMapper(managerBeans)

    @GetMapping("/version")
    fun getVersion(): String {
        return "21.05.2023 20:33"
    }

    @GetMapping("/expenses")
    fun getExpenses(): List<ExpenseDto> {
        val expenses = expenseService.getExpenses()
        val expenseDto = mutableListOf<ExpenseDto>()
        for (expense in expenses) {
            expenseDto.add(expenseMapper.toExpenseDto(expense))
        }
        return expenseDto
    }

//    @GetMapping("/expenses/time")
//    fun getExpensesInRange(@RequestParam(required = true) from: LocalDateTime,
//                           @RequestParam(required = true) to: LocalDateTime): List<Expense> {
//        return listOf(Expense(12.0, "comment", 1, LocalDateTime.now()))
//    }

    @PostMapping("/insert")
    fun insert(@RequestBody expense: Expense): ExpenseDto {
        expenseService.insertExpense(expense)
        return expenseMapper.toExpenseDto(expenseService.getLastInsertedExpense())
    }
}