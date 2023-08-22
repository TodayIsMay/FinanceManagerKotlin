package main

import TransactionMapper
import calculators.MonthCalculator
import entities.Transaction
import entities.dto.TransactionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.PrincipalService
import services.TransactionService
import java.time.LocalDate
import java.util.logging.Logger

@RestController
class TransactionController(
    @Autowired
    val managerBeans: ManagerBeans,
    @Autowired
    val securityConfig: SecurityConfig
) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val transactionService: TransactionService = managerBeans.transactionService()
    private val principalService: PrincipalService = securityConfig.userDetailsService()
    private val transactionMapper: TransactionMapper = TransactionMapper(managerBeans)
    private val monthCalculator = MonthCalculator(LocalDate.now(), LocalDate.of(2023, 6, 29))

    @GetMapping("/version")
    fun getVersion(): String {
        return "14.08.2023 22:31"
    }

    @GetMapping("/transactions/{userLogin}")
    fun getTransactionsByUserLogin(
        @PathVariable userLogin: String,
        @RequestHeader("Authorization") auth: String
    ): List<TransactionDto> {
        log.info("Received user login: $userLogin")
        return transactionMapper.toTransactionDtos(transactionService.getTransactionsByUserLogin(userLogin, auth))
    }

    @PostMapping("/transactions/{userLogin}")
    fun insertTransactionByUserLogin(
        @PathVariable userLogin: String,
        @RequestHeader("Authorization") auth: String,
        @RequestBody transaction: Transaction
    ): TransactionDto {
        log.info("Received user login: $userLogin, transaction: $transaction")
        return transactionMapper.toTransactionDto(transactionService.insertTransactionByLogin(userLogin, auth, transaction))
    }

    @DeleteMapping("/transactions/{transactionId}")
    fun deleteTransactionById(@PathVariable transactionId: Long, @RequestHeader("Authorization") auth: String): String {
        return transactionService.deleteTransactionById(transactionId, auth)
    }
}