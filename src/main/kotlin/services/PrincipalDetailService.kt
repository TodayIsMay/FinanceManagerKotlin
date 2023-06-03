package services

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import repositories.PrincipalRepository

class PrincipalDetailService(jdbcTemplate: JdbcTemplate): UserDetailsService {
    private val principalRepository = PrincipalRepository(jdbcTemplate)
    override fun loadUserByUsername(username: String?): UserDetails {
        return principalRepository.findByUserName(username!!)[0]
    }
}