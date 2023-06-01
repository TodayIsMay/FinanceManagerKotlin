package entities

class User(
    var id: Long,
    var login: String,
    var password: String,
    var deviceId: String
) {
}