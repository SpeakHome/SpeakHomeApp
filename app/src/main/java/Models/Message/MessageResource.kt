package Models.Message

import Models.Contact.ContactResource
import java.util.Date

data class MessageResource(
    var id: Long,
    var content: String,
    var createdAt: Date,
)