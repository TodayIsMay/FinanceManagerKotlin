package entities.dto

import java.time.LocalDateTime

@Deprecated(message = "Transaction entity is used instead of Expense and Income")
class ExpenseDto(
    var id: Long,
    var amount: Double,
    var category: String,
    var comment: String,
    var date: LocalDateTime
) {
}