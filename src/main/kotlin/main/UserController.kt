package main

import entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserController(@Autowired managerBeans: ManagerBeans) {
    private val userService = managerBeans.userService()

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
    fun findUserByLogin(@PathVariable login: String): User {
        return userService.findUserByLogin(login)
    }
}