package Models.Profile

data class CreateProfileResource (
    var userName: String,
    var email: String,
    var password: String,
    var roleId: Int,
)