package Models.Requests

data class LoginRequest(
    var email: String,
    var password: String
)