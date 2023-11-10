package Models.ContactMessageResource

import Models.Contact.ContactResource
import Models.Message.MessageResource

data class ContactMessageResource (
    var id: Long,
    var contact: ContactResource,
    var message: MessageResource
)