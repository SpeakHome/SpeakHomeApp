package Models.PofileDevice

import Models.Device.DeviceResource

data class ProfileDeviceResource(
    var id: Long,
    var profileId: Long,
    var device: DeviceResource
)