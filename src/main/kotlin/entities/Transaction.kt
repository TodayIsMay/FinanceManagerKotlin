package entities

import TransactionType
import org.springframework.lang.Nullable
import java.time.LocalDateTime

class Transaction(
    var transactionType: TransactionType,
    var id: Long,
    var userId: Long?,
    var amount: Double,
    var comment: String?,
    var categoryId: Long,
    @Nullable var creationTimestamp: LocalDateTime = LocalDateTime.now(),
    @Nullable val date: LocalDateTime = LocalDateTime.now()
) {
    override fun toString(): String {
        return "Transaction(transactionType=$transactionType, id=$id, userId=$userId, amount=$amount, " +
                "comment=$comment, categoryId=$categoryId, creationTimestamp=$creationTimestamp, date=$date)"
    }
}