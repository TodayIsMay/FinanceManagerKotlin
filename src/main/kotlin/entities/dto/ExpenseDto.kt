package entities.dto

import java.time.LocalDateTime

class ExpenseDto(
    var id: Long,
    var amount: Double,
    var category: String,
    var comment: String,
    var date: LocalDateTime
) {
}