package Services

import Models.ApiResponse
import Models.Contact.ContactResource
import Models.Location.LocationResource
import retrofit2.Call
import retrofit2.http.GET

interface LocationService {
    @GET("device-iot/locations")
    fun getAll(): Call<ApiResponse<List<LocationResource>>>
}