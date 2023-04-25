import entities.Expense
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class ExpenseMatcher {
    fun matchStringToExpense(stringExpense: String): Expense {
        if (stringExpense.isBlank()) throw IllegalArgumentException("Empty string")

        val array = stringExpense.split(",")
        return Expense(array[2].toDouble(), array[1], LocalDateTime.parse(array[0]))
    }
}