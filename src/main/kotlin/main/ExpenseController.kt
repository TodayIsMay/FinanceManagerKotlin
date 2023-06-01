package main

import ExpenseMapper
import entities.Expense
import entities.dto.ExpenseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.ExpenseService
import services.UserService

@RestController
@RequestMapping
class ExpenseController(
    @Autowired
    val managerBeans: ManagerBeans
) {
    private val expenseService: ExpenseService = managerBeans.expenseService()
    private val userService: UserService = managerBeans.userService()
    private val expenseMapper: ExpenseMapper = ExpenseMapper(managerBeans)

    @GetMapping("/version")
    fun getVersion(): String {
        return "01.06.2023 14:31"
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

    @GetMapping("expenses/{userId}")
    fun getExpensesByUser(@PathVariable userId: Long): List<ExpenseDto> {
        val expenses = expenseService.getExpensesByUser(userId)
        val expenseDto = mutableListOf<ExpenseDto>()
        for (expense in expenses) {
            expenseDto.add(expenseMapper.toExpenseDto(expense))
        }
        return expenseDto
    }

    @GetMapping("expenses/user/{userLogin}")
    fun getExpensesByUserLogin(@PathVariable userLogin: String): List<ExpenseDto> {
        val user = userService.findUserByLogin(userLogin)
        val expenses = expenseService.getExpensesByUser(user.id)
        val expenseDto = mutableListOf<ExpenseDto>()
        for (expense in expenses) {
            expenseDto.add(expenseMapper.toExpenseDto(expense))
        }
        return expenseDto
    }

    @PostMapping("/insert")
    fun insert(@RequestBody expense: Expense): ExpenseDto {
        expenseService.insertExpense(expense)
        return expenseMapper.toExpenseDto(expenseService.getLastInsertedExpense())
    }

    @PostMapping("/insert/{userLogin}")
    fun insertByLogin(@PathVariable userLogin: String, @RequestBody expense: Expense): ExpenseDto {
        val user = userService.findUserByLogin(userLogin)
        expense.userId = user.id
        expenseService.insertExpense(expense)
        return expenseMapper.toExpenseDto(expenseService.getLastInsertedExpense())
    }
}