package services

import entities.Category
import org.springframework.jdbc.core.JdbcTemplate
import repositories.CategoryRepository

class CategoryService(jdbcTemplate: JdbcTemplate) {
    private val categoryRepository = CategoryRepository(jdbcTemplate)

    fun getCategories(): List<Category> {
        return categoryRepository.getCategories()
    }

    fun addCategory(category: Category): Category {
        return categoryRepository.addCategory(category)
    }
}