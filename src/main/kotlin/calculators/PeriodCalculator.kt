package calculators

import entities.Principal
import java.math.RoundingMode
import java.time.LocalDate
import kotlin.math.roundToInt

/**
 * Calculates amount of money, that user can spend until the end of set period. The end of the report period is set
 * in the Principal itself. By default, the end of the report period is the last day of the current month.
 * Start of the report period is always current day.
 */
class PeriodCalculator {
    private val currentMonth = LocalDate.now().month
    private val currentYear = LocalDate.now().year
    private var lastDayOfMonth = LocalDate.now().lengthOfMonth()
    private var start = LocalDate.now()
    private var end = LocalDate.of(currentYear, currentMonth, lastDayOfMonth)

    fun calculate(principal: Principal): Map<LocalDate, Double> {
        if (principal.endReportPeriod != null) {
            end = principal.endReportPeriod
        }
        var amountOfDays = start.datesUntil(end).count()
        if (amountOfDays < 1) {
            amountOfDays = 1
        }
        val amountPerDay = principal.availableFunds/amountOfDays.toDouble()
        val resultExpenseMap = mutableMapOf<LocalDate, Double>()
        resultExpenseMap[start] = amountPerDay
        for (i in 1 until amountOfDays) {
            val previousDayAmount = resultExpenseMap[start.plusDays((i-1))]
            val roundedResult = (previousDayAmount!! + amountPerDay).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            resultExpenseMap[start.plusDays(i)] = roundedResult
        }
        return resultExpenseMap
    }

    //TODO: count how much money was saved
    //TODO: count, using mandatory/not mandatory transactions
}