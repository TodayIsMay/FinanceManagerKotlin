package services

import entities.Principal
import entities.Role
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import repositories.PrincipalRepository
import java.util.*
import java.util.logging.Logger

class PrincipalService(
    jdbcTemplate: JdbcTemplate,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    private val log = Logger.getLogger(this.javaClass.name)
    private val principalRepository = PrincipalRepository(jdbcTemplate)

    fun save(principal: Principal) {
        val principalFromDB = principalRepository.findByUserName(principal.username)
        if (principalFromDB.isNotEmpty()) {
            log.warning("Such user is already in DB!")
            throw IllegalArgumentException("Such user is already in DB!")
        }
        principal.roles = Collections.singleton(Role(null, "ROLE_USER", null))
        val password = bCryptPasswordEncoder.encode(principal.password)
        principal.password = password
        principalRepository.save(principal)
    }

    fun findByUsername(username: String): Principal {
        val principalInList = principalRepository.findByUserName(username)
        if (principalInList.isEmpty()) {
            log.warning("There is no user with username $username in DB!")
            throw IllegalArgumentException("There is no such user in DB")
        }
        if (principalInList.size > 1) {
            log.warning("There are more than one user with username $username. How did this happen?..")
            throw IllegalArgumentException("There are more than one user with username $username. How did this happen?..")
        }
        return principalInList[0]
    }

    fun checkPrincipalPassword(principal: Principal): Boolean {
        val principalFromBD = findByUsername(principal.username)
        return bCryptPasswordEncoder.matches(principal.password, principalFromBD.password)
    }
}