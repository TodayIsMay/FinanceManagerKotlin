package entities

import org.springframework.lang.Nullable
import java.time.LocalDateTime

data class Expense(
    var amount: Double,
    var comment: String?,
    var categoryId: Int,
    @Nullable val date: LocalDateTime = LocalDateTime.now()
)