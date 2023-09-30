package Model

class User {
    var userName: String
    var email: String
    var password: String
    var roleId: Int
    constructor(userName: String, email: String, password: String, roleId: Int) {
        this.userName = userName
        this.email = email
        this.password = password
        this.roleId = roleId
    }
}