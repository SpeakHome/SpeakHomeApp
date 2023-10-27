package Models.Message

import Models.Chat.ChatResource
import java.util.Date

data class MessageResource(
    var id: Long,
    var content: String,
    var createdAt: Date,
    var chat: ChatResource
)