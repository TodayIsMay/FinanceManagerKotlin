package main

import entities.Income
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import services.IncomeService
import java.util.logging.Logger

@Deprecated(message = "Now Transaction Controller is used both for incomes and expenses")
@RestController
class IncomeController(
    @Autowired
    val managerBeans: ManagerBeans
) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val incomeService: IncomeService = managerBeans.incomeService()

    @GetMapping("/incomes/{userLogin}")
    fun getTransactionsByUserLogin(@PathVariable userLogin: String,
                                   @RequestHeader("Authorization") auth: String): List<Income> {
        log.info("Received user for getting incomes: $userLogin")
        return incomeService.getTransactionsByUserLogin(userLogin, auth)
    }

    @PostMapping("/incomes/{userLogin}")
    fun insertByLogin(@PathVariable userLogin: String,
                      @RequestBody income: Income,
                      @RequestHeader("Authorization") auth: String): Income {
        return incomeService.insertTransactionByUserLogin(userLogin, income, auth)
    }
}