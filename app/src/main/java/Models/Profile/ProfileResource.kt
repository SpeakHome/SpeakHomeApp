package Models.Profile

import Models.Role.RoleResource

class ProfileResource {
    constructor(id: Int, userName: String, email: String, password: String, role: RoleResource) {
        this.id = id
        this.userName = userName
        this.email = email
        this.password = password
        this.role = role
    }
    var id: Int
    var userName: String
    var email: String
    var password: String
    var role: RoleResource
}