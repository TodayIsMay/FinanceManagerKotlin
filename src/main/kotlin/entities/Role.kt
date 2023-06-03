package entities

import org.springframework.security.core.GrantedAuthority

class Role(
    private val id: Long?,
    private val name: String,
    private val users: Set<Principal>?
) : GrantedAuthority {

    override fun getAuthority(): String {
        return name
    }
}