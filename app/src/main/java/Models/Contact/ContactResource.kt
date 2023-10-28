package Models.Contact

data class ContactResource(
    var id: Long,
    var profile1Id: Long,
    var profile2Id: Long,
    var devicePermission: Boolean
)