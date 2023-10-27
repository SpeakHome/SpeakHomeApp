package Models.Profile

data class CreateProfile (
    var userName: String,
    var email: String,
    var password: String,
    var roleId: Int,
)