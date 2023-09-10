package main

import UserMapper
import entities.User
import entities.dto.UserDto
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@Tag(name = "Users", description = "Registration and login")
@SecurityRequirement(name = "basicAuth")
@RestController
class UserController(
    @Autowired managerBeans: ManagerBeans, @Autowired
    val securityConfig: SecurityConfig
) {
    private val userService = managerBeans.userService()
    private val principalService = securityConfig.userDetailsService()
    private val userMapper = UserMapper()

    @Hidden
    @PostMapping("users/insert")
    fun insertUser(@RequestBody user: User): String {
        return try {
            userService.insertUser(user)
            "User $user was inserted"
        } catch (e: Exception) {
            "There was exception ${e.message}"
        }
    }

    @Hidden
    @GetMapping("users/{login}")
    fun findUserByLogin(@PathVariable login: String): UserDto {
        return userMapper.toUserDto(principalService.findByUsername(login))
    }

    @PatchMapping("users/{login}")
    fun setEndOfReportPeriodForPrincipal(
        @PathVariable login: String,
        @Parameter(required = false, hidden = true) @RequestHeader("Authorization") auth: String,
        @RequestBody endReportPeriod: LocalDate
    ) {
        principalService.setReportPeriod(login, auth, endReportPeriod)
    }
}