package entities

import org.springframework.lang.Nullable
import java.time.LocalDateTime

data class Income(
    var id: Long,
    var userId: Long?,
    var amount: Double,
    var comment: String?,
    var categoryId: Long,
    @Nullable var creationTimestamp: LocalDateTime = LocalDateTime.now(),
    @Nullable val date: LocalDateTime = LocalDateTime.now()
)