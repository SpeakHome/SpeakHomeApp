package Models.DeviceIot

data class SeekBarCommand(
    val id: Int,
    val name: String,
    val action: String,
    val endpoint: String,
    val method: String,
    val body: Map<String, Any>
)