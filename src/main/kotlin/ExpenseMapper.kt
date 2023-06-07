import entities.Category
import entities.Expense
import entities.dto.ExpenseDto
import exceptions.NoSuchEntityException
import main.ManagerBeans
import java.util.logging.Logger

class ExpenseMapper(
    managerBeans: ManagerBeans
) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val categoryService = managerBeans.categoryService()

    fun toExpenseDto(expense: Expense): ExpenseDto {
        var category = Category(0L, "Unknown category")
        try {
            category = categoryService.getCategoryById(expense.categoryId)
        } catch (e: NoSuchEntityException) {
            log.warning("Category with ${expense.categoryId} was not found, default category was used.")
        }
        var comment = ""
        if (expense.comment != null) {
            comment = expense.comment!!
        }
        return ExpenseDto(expense.id, expense.amount, category.name, comment, expense.date)
    }
}