package entities

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class Principal(
    val id: Long?,
    private val username: String,
    private var password: String,
    var salt: String?,
    var availableFunds: Double,
    var roles: MutableCollection<Role>?
) : UserDetails {

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return roles
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}