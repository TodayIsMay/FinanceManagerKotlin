package services

import TransactionType
import entities.Transaction
import exceptions.NoSuchEntityException
import exceptions.UserNotAuthorizedException
import exceptions.WrongPasswordException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import repositories.PrincipalRepository
import repositories.TransactionRepository
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.logging.Logger

class TransactionService(jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    private val transactionRepository: TransactionRepository = TransactionRepository(jdbcTemplate)
    private val principalRepository: PrincipalRepository = PrincipalRepository(jdbcTemplate)

    fun getTransactionsByUserLogin(userLogin: String, auth: String): List<Transaction> {
        checkAuth(userLogin, auth)
        val principal = principalRepository.findByUserName(userLogin)
        if (principal.size > 1) {
            throw exceptions.IllegalArgumentException("There more than 1 user with login $userLogin")
        }
        return transactionRepository.getTransactionsByUserId(principal[0].id!!)
    }

    fun insertTransactionByLogin(userLogin: String, auth: String, transaction: Transaction): Transaction {
        checkAuth(userLogin, auth)
        val principalList = principalRepository.findByUserName(userLogin)
        if (principalList.size > 1) {
            throw exceptions.IllegalArgumentException("There more than 1 user with login $userLogin")
        }
        if (principalList.isEmpty()) {
            throw NoSuchEntityException("There is no user with login $userLogin")
        }
        val principal = principalList[0]
        if (transaction.transactionType == TransactionType.EXPENSE) {
            principalRepository.setAvailableFundsToPrincipal(
                principal.id!!,
                principal.availableFunds - transaction.amount
            )
        } else {
            principalRepository.setAvailableFundsToPrincipal(
                principal.id!!,
                principal.availableFunds + transaction.amount
            )
        }
        transaction.userId = principal.id
        transactionRepository.insertTransaction(transaction)
        return transactionRepository.getLastInsertedTransaction(principal.id)
    }

    fun deleteTransactionById(transactionId: Long, auth: String): String {
        if (auth.isBlank()) {
            log.severe("Only authorized users can delete transactions!")
            throw UserNotAuthorizedException("Who are you? Only authorized users can delete transactions!")
        }
        val transactionList = transactionRepository.getTransactionById(transactionId)
        if (transactionList.size > 1) {
            log.severe("There are more than 1 transaction with id $transactionId")
            throw exceptions.IllegalArgumentException("There are more than 1 transaction with id $transactionId")
        }
        if (transactionList.isEmpty()) {
            log.info("There are no any transaction with id $transactionId")
            throw NoSuchEntityException("There are no any transaction with id $transactionId")
        }
        if (!checkUser(transactionList[0], auth)) {
            log.severe("Only owner of transaction can delete it!")
            throw exceptions.IllegalArgumentException("Only owner of transaction can delete it!")
        }
        transactionRepository.deleteTransactionById(transactionId)
        log.info("Transaction with id $transactionId was deleted!")
        val principal = principalRepository.findById(transactionList[0].userId!!)
        if (transactionList[0].transactionType == TransactionType.EXPENSE) {
            principalRepository.setAvailableFundsToPrincipal(
                principal[0].id!!,
                principal[0].availableFunds + transactionList[0].amount
            )
        } else {
            principalRepository.setAvailableFundsToPrincipal(
                principal[0].id!!,
                principal[0].availableFunds - transactionList[0].amount
            )
        }
        return "Transaction was deleted!"
    }

    private fun checkAuth(userLogin: String, auth: String) {
        if (auth.isBlank()) {
            throw UserNotAuthorizedException("Who are you? You're not even authorized.")
        }
        if (userLogin.isNotBlank()) {
            val principalInList = principalRepository.findByUserName(userLogin)
            if (principalInList.isEmpty()) {
                log.warning("There is no user with username $userLogin in DB!")
                throw IllegalArgumentException("There is no such user in DB")
            }

            if (principalInList.size > 1) {
                log.warning("There are more than one user with username $userLogin. How did this happen?..")
                throw IllegalArgumentException("There are more than one user with username $userLogin. How did this happen?..")
            }

            val encodedPassword = principalInList[0].password
            val onlyPassword = auth.substring("Basic".length).trim()
            val decodedBasic = Base64.getDecoder().decode(onlyPassword).toString(StandardCharsets.UTF_8)
            val values = decodedBasic.split(":")
            if (!bCryptPasswordEncoder.matches(values[1], encodedPassword)) {
                log.warning("Wrong password!")
                throw WrongPasswordException("Wrong password!")
            }
        } else {
            throw java.lang.IllegalArgumentException("UserId can't be null")
        }
    }

    private fun checkUser(transaction: Transaction, auth: String): Boolean {
        val principalList = principalRepository.findById(transaction.userId!!)
        if (principalList.size > 1) {
            throw exceptions.IllegalArgumentException("There are more than 1 user with id ${transaction.userId}")
        }
        if (principalList.isEmpty()) {
            throw NoSuchEntityException("There are no any user with id ${transaction.userId}")
        }
        val login = getLoginFromAuth(auth)
        checkAuth(login, auth)
        return principalList[0].username.equals(login)
    }

    private fun getLoginFromAuth(auth: String): String {
        val onlyPassword = auth.substring("Basic".length).trim()
        val decodedBasic = Base64.getDecoder().decode(onlyPassword).toString(StandardCharsets.UTF_8)
        val values = decodedBasic.split(":")
        return values[0]
    }
}