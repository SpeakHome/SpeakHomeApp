package Models.Device

data class UpdateDeviceResource(
    var name: String,
    var baseUrl: String,
    var description: String,
    var pictureUrl: String
)
