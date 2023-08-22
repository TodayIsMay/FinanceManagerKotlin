import entities.Principal
import entities.dto.UserDto

class UserMapper(
) {
    fun toUserDto(principal: Principal): UserDto {
        return UserDto(principal.id!!, principal.username, principal.availableFunds)
    }
}