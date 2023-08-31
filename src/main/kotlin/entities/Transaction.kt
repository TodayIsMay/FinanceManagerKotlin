package entities

import TransactionType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.lang.Nullable
import java.time.LocalDateTime

class Transaction(
    var transactionType: TransactionType,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var id: Long,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var userId: Long?,
    var amount: Double,
    var comment: String?,
    var categoryId: Long,
    var isMandatory: Boolean,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Nullable var creationTimestamp: LocalDateTime = LocalDateTime.now(),
    @Nullable val date: LocalDateTime = LocalDateTime.now()
) {
    override fun toString(): String {
        return "Transaction(transactionType=$transactionType, id=$id, userId=$userId, amount=$amount, " +
                "comment=$comment, categoryId=$categoryId, creationTimestamp=$creationTimestamp, date=$date)"
    }
}