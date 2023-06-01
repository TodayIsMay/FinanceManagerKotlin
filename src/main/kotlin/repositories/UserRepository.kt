package repositories

import entities.User
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.ResultSet

class UserRepository(private val jdbcTemplate: JdbcTemplate) {
    fun insertUser(user: User) {
        val sql = "INSERT INTO users (login, password, device_id) VALUES (?, ?, ?)"
        jdbcTemplate.update(sql, user.login, user.password, user.deviceId)
    }

    fun findUserByLogin(login: String): User {
        val sql = "SELECT * FROM users where login = '$login'"
        val user = jdbcTemplate.query(sql) { rs: ResultSet, _: Int ->
            User(
                rs.getLong("id"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("device_id")
            )
        }[0]
        return user
    }
}