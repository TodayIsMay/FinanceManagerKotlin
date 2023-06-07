package services

import entities.Category
import exceptions.IllegalArgumentException
import exceptions.NoSuchEntityException
import exceptions.UserNotAuthorizedException
import org.springframework.jdbc.core.JdbcTemplate
import repositories.CategoryRepository
import java.util.logging.Logger

class CategoryService(jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val categoryRepository = CategoryRepository(jdbcTemplate)

    fun getCategories(): List<Category> {
        log.info("Trying to get categories...")
        return categoryRepository.getCategories()
    }

    fun getCategoryById(id: Long): Category {
        log.info("Trying to get category with id $id")
        val category = categoryRepository.getCategoryById(id)
        if (category.isEmpty()) {
            log.warning("Such category is not exists!")
            throw NoSuchEntityException("Such category is not exists!")
        }
        if (category.size > 1) {
            log.warning("There are more than 1 category with id $id. :/")
            throw IllegalArgumentException("There are more than 1 category with id $id.")
        }
        return category[0]
    }

    fun addCategory(category: Category, auth: String): Category {
        if (auth.isNullOrBlank()) {
            throw UserNotAuthorizedException("Only authorized users can add categories")
        }
        log.info("Adding category: $category")
        categoryRepository.addCategory(category)
        return categoryRepository.getLastInsertedCategory()
    }

    fun deleteCategory(id: Long, auth: String): String {
        if (auth.isNullOrBlank()) {
            log.warning("Only authorized users can delete categories")
            throw UserNotAuthorizedException("Only authorized users can delete categories")
        }
        log.info("Deleting category with id $id...")
        return categoryRepository.deleteCategory(id)
    }
}