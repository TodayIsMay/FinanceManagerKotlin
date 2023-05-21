package repositories

import entities.Category
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class CategoryRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getCategories(): List<Category> {
        val sql = "SELECT * FROM categories"
        val list = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Category(
                rs.getString("name")
            )
        }
        return list
    }

    fun addCategory(category: Category): Category {
        jdbcTemplate.update(
            "INSERT INTO categories (name) VALUES (?)",
            category.name
        )
        return category
    }
}