package repositories

import entities.Category
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet
import java.util.logging.Logger

class CategoryRepository(private val jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

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

    fun getLastInsertedCategory(): Category {
        val sql = "SELECT * FROM categories ORDER BY id DESC LIMIT 1"
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

    fun deleteCategory(id: Long): String {
        val sql = "DELETE FROM categories WHERE id = (?)"
        try {
            jdbcTemplate.update(sql, id)
        } catch (e: Exception) {
            log.warning("There was an exception during deleting category. :(")
        }
        return "Category with id $id was deleted!"
    }
}