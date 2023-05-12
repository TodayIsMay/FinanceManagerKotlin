import entities.Expense
import java.time.LocalDateTime

class ExpenseMapper {
    fun matchStringToExpense(stringExpense: String): Expense {
        if (stringExpense.isBlank()) throw IllegalArgumentException("Empty string")

        val array = stringExpense.split(",")
        return Expense(array[2].toDouble(), array[1], 1, LocalDateTime.now())
    }
}