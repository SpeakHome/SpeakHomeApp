package Models.Device

data class CreateDeviceResource (
    var name: String,
    var baseUrl: String,
    var description: String,
    var pictureUrl: String,
    var locationId: Long,
    var deviceStatusId:Long
)