package Models.Chat

data class ChatResource(
    var id: Long,
    var profile1Id: Long,
    var profile2Id: Long,
    var devicePermission: Boolean
)