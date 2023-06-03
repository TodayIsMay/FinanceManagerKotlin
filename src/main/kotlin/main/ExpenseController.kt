package main

import ExpenseMapper
import entities.Expense
import entities.dto.ExpenseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.ExpenseService
import services.PrincipalService
import services.UserService

@RestController
@RequestMapping
class ExpenseController(
    @Autowired
    val managerBeans: ManagerBeans,
    @Autowired
    val securityConfig: SecurityConfig
) {
    private val expenseService: ExpenseService = managerBeans.expenseService()
    private val userService: UserService = managerBeans.userService()
    private val principalService: PrincipalService = securityConfig.userDetailsService()
    private val expenseMapper: ExpenseMapper = ExpenseMapper(managerBeans)

    @GetMapping("/version")
    fun getVersion(): String {
        return "03.06.2023 18:05"
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
    fun getExpensesByUser(@PathVariable userId: Long, @RequestHeader("Authorization") auth: String)
            : List<ExpenseDto> {
        val expenses = expenseService.getExpensesByUser(userId, auth)
        val expenseDto = mutableListOf<ExpenseDto>()
        for (expense in expenses) {
            expenseDto.add(expenseMapper.toExpenseDto(expense))
        }
        return expenseDto
    }

    @GetMapping("expenses/user/{userLogin}")
    fun getExpensesByUserLogin(@PathVariable userLogin: String, @RequestHeader("Authorization") auth: String): List<ExpenseDto> {
        val user = principalService.findByUsername(userLogin)
        val expenses = expenseService.getExpensesByUser(user.id, auth)
        val expenseDto = mutableListOf<ExpenseDto>()
        for (expense in expenses) {
            expenseDto.add(expenseMapper.toExpenseDto(expense))
        }
        return expenseDto
    }

    @PostMapping("/insert")
    fun insert(@RequestBody expense: Expense, @RequestHeader("Authorization") auth: String): ExpenseDto {
        expenseService.insertExpense(expense, auth)
        return expenseMapper.toExpenseDto(expenseService.getLastInsertedExpense())
    }

    @PostMapping("/insert/{userLogin}")
    fun insertByLogin(@PathVariable userLogin: String,
                      @RequestBody expense: Expense,
                      @RequestHeader("Authorization") auth: String): ExpenseDto {
        val user = principalService.findByUsername(userLogin)
        expense.userId = user.id
        expenseService.insertExpense(expense, auth)
        return expenseMapper.toExpenseDto(expenseService.getLastInsertedExpense())
    }
}