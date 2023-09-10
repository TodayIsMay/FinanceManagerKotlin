package entities

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

class Principal(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long?,
    private val username: String,
    private var password: String,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var salt: String?,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var availableFunds: Double,
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var roles: MutableCollection<Role>?
) : UserDetails {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var startReportPeriod: LocalDate? = null

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    var endReportPeriod: LocalDate? = null

    constructor(
        id: Long,
        username: String,
        password: String,
        salt: String?,
        availableFunds: Double,
        roles: MutableCollection<Role>?,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        startReportPeriod: LocalDate?,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        endReportPeriod: LocalDate?
    ) : this(id, username, password, salt, availableFunds, roles) {
        this.startReportPeriod = startReportPeriod
        this.endReportPeriod = endReportPeriod
    }

    fun setPassword(password: String) {
        this.password = password
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return roles
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    override fun isEnabled(): Boolean {
        return true
    }
}