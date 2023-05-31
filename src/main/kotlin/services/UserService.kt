package services

import entities.User
import org.springframework.jdbc.core.JdbcTemplate
import repositories.UserRepository
import java.util.logging.Logger

class UserService(jdbcTemplate: JdbcTemplate) {
    private val log = Logger.getLogger(this.javaClass.name)

    private val userRepository = UserRepository(jdbcTemplate)

    fun insertUser(user: User) {
        userRepository.insertUser(user)
    }

    fun findUserByLogin(login: String): User {
        return userRepository.findUserByLogin(login)
    }
}