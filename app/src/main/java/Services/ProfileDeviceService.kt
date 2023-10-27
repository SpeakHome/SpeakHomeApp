package Services

import Models.ApiResponse
import Models.PofileDevice.ProfileDeviceResource
import Models.Profile.CreateProfile
import Models.Profile.ProfileResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileDeviceService {
    @GET("device-iot/profile-devices")
    fun getAll(): Call<ApiResponse<List<ProfileDeviceResource>>>

    @GET("device-iot/profile-devices/{id}")
    fun getById(@Path("id") id: Int): Call<ProfileDeviceResource>

    //@POST("device-iot/profile-devices")
    //fun create(@Body newObject: CreateProfileDevice): Call<ProfileDeviceResource>
}