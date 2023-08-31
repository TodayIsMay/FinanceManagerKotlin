import entities.Category
import entities.Transaction
import entities.dto.TransactionDto
import exceptions.NoSuchEntityException
import main.ManagerBeans
import java.util.logging.Logger

class TransactionMapper(
    managerBeans: ManagerBeans
) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val categoryService = managerBeans.categoryService()

    fun toTransactionDto(transaction: Transaction): TransactionDto {
        var category = Category(0L, "Unknown category")
        try {
            category = categoryService.getCategoryById(transaction.categoryId)
        } catch (e: NoSuchEntityException) {
            log.warning("Category with ${transaction.categoryId} was not found, default category was used.")
        }
        var comment = ""
        if (transaction.comment != null) {
            comment = transaction.comment!!
        }
        var amount = transaction.amount
        if (transaction.transactionType == TransactionType.EXPENSE) {
            amount = 0 - transaction.amount
        }
        return TransactionDto(
            transaction.id, transaction.transactionType, amount, category.name, comment,
            transaction.date, transaction.isMandatory
        )
    }

    fun toTransactionDtos(transactions: List<Transaction>): List<TransactionDto> {
        val transactionDtoList = mutableListOf<TransactionDto>()
        for (transaction in transactions) {
            transactionDtoList.add(toTransactionDto(transaction))
        }
        return transactionDtoList
    }
}