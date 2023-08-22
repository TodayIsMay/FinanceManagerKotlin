package main

import entities.Principal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegistrationController(
    @Autowired securityConfig: SecurityConfig,
) {
    private val principalService = securityConfig.userDetailsService()

    @PostMapping("/registration")
    fun register(@RequestBody principal: Principal): String {
        try {
            principalService.save(principal)
        } catch (e: IllegalArgumentException) {
            return e.message.toString()
        }
        return "User was registered"
    }

    @PostMapping("/login")
    fun login(@RequestBody principal: Principal): String {
        if (principalService.checkPrincipalPassword(principal)) {
            return "User ${principal.username} was signed in!"
        }
        return "Signing in failed"
    }
}