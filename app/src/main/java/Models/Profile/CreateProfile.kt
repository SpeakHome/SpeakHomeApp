package Models.Profile

class CreateProfile {
    constructor(userName: String, email: String, password: String, roleId: Int) {
        this.userName = userName
        this.email = email
        this.password = password
        this.roleId = roleId
    }

    var userName: String
    var email: String
    var password: String
    var roleId: Int
}