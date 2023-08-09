package calculators

import java.time.LocalDate

class MonthCalculator(val start: LocalDate, var end: LocalDate) {

    fun calculate(availableFunds: Double): Map<LocalDate, Double> {
        val amountOfDays = end.dayOfMonth - start.dayOfMonth
        val amountPerDay = availableFunds/amountOfDays.toDouble()
        val resultExpenseMap = mutableMapOf<LocalDate, Double>()
        resultExpenseMap[start] = amountPerDay
        for (i in 1 until amountOfDays) {
            val previousDayAmount = resultExpenseMap[start.plusDays((i-1).toLong())]
            resultExpenseMap[start.plusDays(i.toLong())] = (previousDayAmount!! + amountPerDay)
        }
        return resultExpenseMap
    }

    //TODO: сделать расчёт, сколько сэкономлено деняк
    //TODO: научить делать рассчёт на основе обязательных/необязательных расходов
}