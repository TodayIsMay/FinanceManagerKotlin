package entities

import java.time.LocalDateTime

data class Expense(var amount: Double,
                   var comment: String?,
                   var date: LocalDateTime)