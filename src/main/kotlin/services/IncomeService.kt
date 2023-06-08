package services

import entities.Income
import exceptions.UserNotAuthorizedException
import exceptions.WrongPasswordException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import repositories.IncomeRepository
import repositories.PrincipalRepository
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.logging.Logger

class IncomeService(jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    private val incomeRepository: IncomeRepository = IncomeRepository(jdbcTemplate)
    private val principalRepository: PrincipalRepository = PrincipalRepository(jdbcTemplate)

    fun getTransactionsByUserLogin(userLogin: String, auth: String): List<Income> {
        checkAuth(userLogin, auth)
        val principal = principalRepository.findByUserName(userLogin)
        if (principal.size > 1) {
            throw exceptions.IllegalArgumentException("There more than 1 user with login $userLogin")
        }
        return incomeRepository.getTransactionsByUserId(principal[0].id!!)
    }

    fun insertTransactionByUserLogin(userLogin: String, income: Income, auth: String): Income {
        checkAuth(userLogin, auth)
        val principal = principalRepository.findByUserName(userLogin)
        if (principal.size > 1) {
            throw exceptions.IllegalArgumentException("There are more than 1 user with login $userLogin")
        }
        income.userId = principal[0].id
        incomeRepository.insertTransaction(income)
        val resultedAvailableFunds = principal[0].availableFunds + income.amount
        principalRepository.setAvailableFundsToPrincipal(principal[0].id!!, resultedAvailableFunds)
        return incomeRepository.getLastInsertedExpenseByUserId(principal[0].id!!)
    }

    private fun checkAuth(userLogin: String, auth: String) {
        if (auth.isBlank()) {
            throw UserNotAuthorizedException("Who are you? You're not even authorized.")
        }
        if (userLogin != null) {
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
}