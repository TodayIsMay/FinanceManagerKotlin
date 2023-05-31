package repositories

import entities.Category
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class CategoryRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getCategories(): List<Category> {
        val sql = "SELECT * FROM categories"
        val list = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Category(
                rs.getLong("id"),
                rs.getString("name")
            )
        }
        return list
    }

    fun getCategoryById(id: Long): Category {
        val sql = "SELECT * FROM categories WHERE id = $id"
        val category = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Category(
                rs.getLong("id"),
                rs.getString("name")
            )
        }[0]
        return category
    }

    fun addCategory(category: Category): Category {
        jdbcTemplate.update(
            "INSERT INTO categories (name) VALUES (?)",
            category.name
        )
        return category
    }
}