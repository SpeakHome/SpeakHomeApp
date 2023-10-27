package Models.PofileDevice

import Models.Device.DeviceResource

class ProfileDeviceResource(
    var id: Long,
    var profileId: Long,
    var device: DeviceResource
)