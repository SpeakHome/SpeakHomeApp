package Models.Device

import Models.DeviceStatus.DeviceStatusResource
import Models.Location.LocationResource
import Models.PofileDevice.ProfileDeviceResource

data class DeviceResource(
    var id: Long,
    var name: String,
    //var type: String,
    //var data: String,
    var baseUrl: String,
    //var lastUpdate: String,
    //var isOnline: Boolean,
    var description: String,
    var pictureUrl: String,
    var location: LocationResource,
    var deviceStatus: DeviceStatusResource,
)
