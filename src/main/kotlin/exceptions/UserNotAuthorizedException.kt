package exceptions

class UserNotAuthorizedException(override var message: String): Exception() {
}