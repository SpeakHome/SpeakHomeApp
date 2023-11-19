package Models.Contact
data class CreateContactResource (
    var profileId: Long,
    var contactProfileId: Long,
    var devicePermission: Boolean
)