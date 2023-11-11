package Models.Profile

import Models.Role.RoleResource

data class ProfileResource(
    var id: Long,
    var userName: String,
    var email: String,
    var password: String,
    var role: RoleResource
)