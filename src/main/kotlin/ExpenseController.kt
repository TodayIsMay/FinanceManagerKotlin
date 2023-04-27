import db.ExpenseFileRepository
import entities.Expense
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class ExpenseController {
    private val expenseFileDAO: ExpenseFileRepository = ExpenseFileRepository()

    @GetMapping("/expenses")
    fun getExpenses(): List<Expense> {
        return expenseFileDAO.getExpenses()
    }

    @GetMapping("/expenses")
    fun getExpensesInRange(@RequestParam(required = true) from: LocalDateTime,
                           @RequestParam(required = true) to: LocalDateTime): List<Expense> {
        return listOf(Expense(12.0, "comment", LocalDateTime.now()))
    }
}