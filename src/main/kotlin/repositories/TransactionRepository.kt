package repositories

import TransactionType
import entities.Transaction
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class TransactionRepository(private val jdbcTemplate: JdbcTemplate) {
    fun getTransactionsByUserId(id: Long): List<Transaction> {
        val sql = "SELECT * FROM transactions WHERE user_id = $id ORDER BY transaction_timestamp DESC"
        val list = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Transaction(
                TransactionType.valueOf(rs.getString("transaction_type")),
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("transaction_timestamp").toLocalDateTime()
            )
        }
        return list
    }

    fun insertTransaction(transaction: Transaction) {
        val sql = "INSERT INTO transactions (user_id, comment, amount, category_id, transaction_type, " +
                "transaction_timestamp, creation_timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)"
        jdbcTemplate.update(
            sql,
            transaction.userId,
            transaction.comment,
            transaction.amount,
            transaction.categoryId,
            transaction.transactionType.name,
            transaction.date,
            transaction.creationTimestamp
        )
    }

    fun getLastInsertedTransaction(userId: Long): Transaction {
        val sql = "SELECT * FROM transactions WHERE user_id = $userId ORDER BY creation_timestamp DESC LIMIT 1"
        return jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Transaction(
                TransactionType.valueOf(rs.getString("transaction_type")),
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("transaction_timestamp").toLocalDateTime()
            )
        }[0]
    }

    fun getTransactionById(transactionId: Long): List<Transaction> {
        val sql = "SELECT * FROM transactions WHERE id = $transactionId"
        return jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Transaction(
                TransactionType.valueOf(rs.getString("transaction_type")),
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getDouble("amount"),
                rs.getString("comment"),
                rs.getLong("category_id"),
                rs.getTimestamp("creation_timestamp").toLocalDateTime(),
                rs.getTimestamp("transaction_timestamp").toLocalDateTime()
            )
        }
    }

    fun deleteTransactionById(transactionId: Long) {
        val sql = "DELETE from transactions WHERE id = ?"
        jdbcTemplate.update(sql, transactionId)
    }
}