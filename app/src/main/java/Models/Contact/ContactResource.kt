package Models.Contact

import Models.Profile.ProfileResource

data class ContactResource(
    var id: Long,
    var profileId: Long,
    var contactProfile: ProfileResource,
    var devicePermission: Boolean
)