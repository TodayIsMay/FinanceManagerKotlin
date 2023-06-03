package services

import exceptions.UserNotAuthorizedException
import entities.Expense
import exceptions.WrongPasswordException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import repositories.ExpenseRepository
import repositories.PrincipalRepository
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.logging.Logger

class ExpenseService(jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    private val expenseRepository: ExpenseRepository = ExpenseRepository(jdbcTemplate)
    private val principalRepository: PrincipalRepository = PrincipalRepository(jdbcTemplate)

    fun getExpenses(): List<Expense> {
        log.info("Trying to get expenses...")
        return expenseRepository.getExpenses()
    }

    fun getLastInsertedExpense(): Expense {
        log.info("Trying to get last inserted expense...")
        return expenseRepository.getLastInsertedExpense()
    }

    fun getExpensesByUser(userId: Long?, auth: String): List<Expense> {
        checkPrincipal(userId, auth)
        return expenseRepository.getExpensesByUser(userId!!)
    }

    fun sortExpenses() {

    }

    fun insertExpense(expense: Expense, auth: String): Expense {
        checkPrincipal(expense.userId, auth)
        log.info("Inserting expense $expense")
        return expenseRepository.insertExpense(expense)
    }

    private fun checkPrincipal(userId: Long?, auth: String) {
        if (auth.isNullOrBlank()) {
            throw UserNotAuthorizedException("Who are you? I didn't call you!")
        }

        if (userId != null) {
            val principalInList = principalRepository.findById(userId)
            if (principalInList.isEmpty()) {
                log.warning("There is no user with username $userId in DB!")
                throw IllegalArgumentException("There is no such user in DB")
            }

            if (principalInList.size > 1) {
                log.warning("There are more than one user with username $userId. How did this happen?..")
                throw IllegalArgumentException("There are more than one user with username $userId. How did this happen?..")
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
}