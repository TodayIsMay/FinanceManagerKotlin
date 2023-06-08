package repositories

import entities.Transaction

interface TransactionRepositoryInterface {
    fun getTransactions(): List<Transaction>
}