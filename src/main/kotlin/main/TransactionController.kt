package main

import TransactionMapper
import calculators.PeriodCalculator
import entities.Transaction
import entities.dto.TransactionDto
import exceptions.UserNotAuthorizedException
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.PrincipalService
import services.TransactionService
import java.time.LocalDate
import java.util.logging.Logger

@RestController
@Tag(name = "Transactions", description = "Operations with transactions")
@SecurityRequirement(name = "basicAuth")
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
    private val periodCalculator = PeriodCalculator()

    @GetMapping("/version")
    fun getVersion(): String {
        return "14.08.2023 22:31"
    }

    @GetMapping("/transactions/{userLogin}")
    fun getTransactionsByUserLogin(
        @PathVariable userLogin: String,
        @Parameter(required = false, hidden = true) @RequestHeader(
            required = false,
            name = "Authorization"
        ) auth: String?
    ): List<TransactionDto> {
        if (auth == null) {
            log.severe("Authorization required for principal $userLogin")
            throw UserNotAuthorizedException("Authorization required")
        }
        log.info("Received user login: $userLogin")
        return transactionMapper.toTransactionDtos(transactionService.getTransactionsByUserLogin(userLogin, auth))
    }

    @PostMapping("/transactions/{userLogin}")
    fun insertTransactionByUserLogin(
        @PathVariable userLogin: String,
        @Parameter(required = false, hidden = true) @RequestHeader("Authorization") auth: String,
        @RequestBody transaction: Transaction
    ): TransactionDto {
        log.info("Received user login: $userLogin, transaction: $transaction")
        return transactionMapper.toTransactionDto(
            transactionService.insertTransactionByLogin(
                userLogin,
                auth,
                transaction
            )
        )
    }

    @DeleteMapping("/transactions/{transactionId}")
    fun deleteTransactionById(
        @PathVariable transactionId: Long,
        @Parameter(required = false, hidden = true) @RequestHeader("Authorization") auth: String
    ): String {
        return transactionService.deleteTransactionById(transactionId, auth)
    }

    @GetMapping("/calculator/current/{userLogin}")
    fun calculate(
        @Parameter(required = false, hidden = true) @RequestHeader("Authorization") auth: String,
        @PathVariable userLogin: String
    ): Map<LocalDate, Double> {
        return transactionService.calculate(auth, userLogin, null)
    }

    @GetMapping("/calculator/given_amount/{userLogin}/{prospectiveAmount}")
    fun calculateFromGivenAmount(
        @Parameter(required = false, hidden = true) @RequestHeader("Authorization") auth: String,
        @PathVariable userLogin: String,
        @PathVariable prospectiveAmount: Double
    ): Map<LocalDate, Double> {
        return transactionService.calculate(auth, userLogin, prospectiveAmount)
    }

    @PatchMapping("/transactions/{transactionId}")
    fun editTransactionById(
        @Parameter(required = false, hidden = true) @RequestHeader("Authorization") auth: String,
        @PathVariable transactionId: Long,
        @RequestBody editedTransaction: Transaction
    ): TransactionDto {
        return transactionMapper.toTransactionDto(transactionService.editTransactionById(auth, transactionId, editedTransaction))
    }
}