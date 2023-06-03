package repositories

import entities.Principal
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class PrincipalRepository(private val jdbcTemplate: JdbcTemplate) {

    fun save(principal: Principal) {
        val sql = "INSERT INTO principals (username, password) VALUES (?, ?)"
        jdbcTemplate.update(sql, principal.username, principal.password)
    }

    fun findByUserName(username: String): List<Principal> {
        val sql = "SELECT * FROM principals WHERE username = '$username'"
        val set = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Principal(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                mutableSetOf()
            )
        }
        return set
    }

    fun findById(id: Long): List<Principal> {
        val sql = "SELECT * FROM principals WHERE id = $id"
        val set = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            Principal(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("password"),
                mutableSetOf()
            )
        }
        return set
    }
}