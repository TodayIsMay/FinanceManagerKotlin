import entities.Transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TransactionServiceTest {
    val transactionIncome100 = Transaction(
        TransactionType.INCOME,
        1L,
        1L,
        100.0,
        "comment",
        1L,
        false,
        LocalDateTime.now(),
        LocalDateTime.now()
    )

    val transactionExpense100 = Transaction(
        TransactionType.EXPENSE,
        1L,
        1L,
        100.0,
        "comment",
        1L,
        false,
        LocalDateTime.now(),
        LocalDateTime.now()
    )

    val editedTransactionExpense150 = Transaction(
        TransactionType.EXPENSE,
        1L,
        1L,
        150.0,
        "comment",
        1L,
        false,
        LocalDateTime.now(),
        LocalDateTime.now()
    )

    val editedTransactionIncome150 = Transaction(
        TransactionType.INCOME,
        1L,
        1L,
        150.0,
        "comment",
        1L,
        false,
        LocalDateTime.now(),
        LocalDateTime.now()
    )

    private fun algorithm(availableFunds: Double, transaction: Transaction, editedTransaction: Transaction): Double {
        val editedFunds: Double
        if (transaction.transactionType != editedTransaction.transactionType) {
            if (editedTransaction.transactionType == TransactionType.EXPENSE) {
                editedFunds = availableFunds - transaction.amount - editedTransaction.amount
            } else {
                editedFunds = availableFunds + transaction.amount + editedTransaction.amount
            }
        } else {
            if (editedTransaction.transactionType == TransactionType.EXPENSE) {
                editedFunds = availableFunds + transaction.amount - editedTransaction.amount
            } else {
                editedFunds = availableFunds - transaction.amount + editedTransaction.amount
            }
        }
        return editedFunds
    }

    @Test
    fun editExpenseEditsAvailableFundsTest() {
        Assertions.assertEquals(1750.0, algorithm(2000.0, transactionIncome100, editedTransactionExpense150))
        Assertions.assertEquals(1950.0, algorithm(2000.0, transactionExpense100, editedTransactionExpense150))
        Assertions.assertEquals(2250.0, algorithm(2000.0, transactionExpense100, editedTransactionIncome150))
        Assertions.assertEquals(2050.0, algorithm(2000.0, transactionIncome100, editedTransactionIncome150))
    }
}