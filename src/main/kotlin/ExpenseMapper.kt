import entities.Expense
import entities.dto.ExpenseDto
import main.ManagerBeans

class ExpenseMapper(
    managerBeans: ManagerBeans
) {

    private val categoryService = managerBeans.categoryService()

    fun toExpenseDto(expense: Expense): ExpenseDto {
        val category = categoryService.getCategoryById(expense.categoryId)
        var comment = ""
        if (expense.comment != null) {
            comment = expense.comment!!
        }
        return ExpenseDto(expense.id, expense.amount, category.name, comment, expense.date)
    }
}