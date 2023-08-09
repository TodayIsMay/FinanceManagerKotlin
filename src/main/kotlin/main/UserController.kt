package main

import entities.Principal
import entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    @Autowired managerBeans: ManagerBeans, @Autowired
    val securityConfig: SecurityConfig
) {
    private val userService = managerBeans.userService()
    private val principalService = securityConfig.userDetailsService();

    @PostMapping("users/insert")
    fun insertUser(@RequestBody user: User): String {
        return try {
            userService.insertUser(user)
            "User $user was inserted"
        } catch (e: Exception) {
            "There was exception ${e.message}"
        }
    }

    @GetMapping("users/{login}")
    fun findUserByLogin(@PathVariable login: String): Principal {
        //return userService.findUserByLogin(login)
        return principalService.findByUsername(login)
    }
}