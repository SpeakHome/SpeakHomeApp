package Models.ContactMessageResource

import Models.Contact.ContactResource
import Models.Message.MessageResource

data class CreateContactMessageResource (
    var contactId: Long,
    var messageId: Long
)