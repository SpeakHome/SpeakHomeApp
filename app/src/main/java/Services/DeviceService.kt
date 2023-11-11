package Services

import Models.Device.CreateDeviceResource
import Models.Device.DeviceResource
import Models.Device.UpdateDeviceResource
import Models.PofileDevice.CreateProfileDeviceResource
import Models.PofileDevice.ProfileDeviceResource
import Models.Profile.ProfileResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeviceService {
    @GET("device-iot/devices/{id}")
    fun getById(@Path("id") id: Int): Call<DeviceResource>

    // Define el endpoint para actualizar el dispositivo
    @PUT("device-iot/devices/{id}")
    fun update(@Path("id") id: Int, @Body updatedDevice: UpdateDeviceResource): Call<DeviceResource>

    @POST("device-iot/devices")
    fun create(@Body newObject: CreateDeviceResource): Call<DeviceResource>

    @DELETE("device-iot/devices/{id}")
    fun delete(@Path("id") id: Int): Call<Void>
}