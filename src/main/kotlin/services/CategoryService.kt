package services

import entities.Category
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

    fun addCategory(category: Category): Category {
        log.info("Adding category: $category")
        return categoryRepository.addCategory(category)
    }
}