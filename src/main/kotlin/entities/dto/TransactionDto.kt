package entities.dto

import TransactionType
import java.time.LocalDateTime

class TransactionDto(
    var id: Long,
    var transactionType: TransactionType,
    var amount: Double,
    var category: String,
    var comment: String,
    var date: LocalDateTime
) {
}