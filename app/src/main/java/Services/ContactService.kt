package Services

import Models.ApiResponse
import Models.Contact.ContactResource
import Models.PofileDevice.ProfileDeviceResource
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ContactService {
    @GET("media-outlet/contacts")
    fun getAll(): Call<ApiResponse<List<ContactResource>>>

    @GET("media-outlet/contacts/{id}")
    fun getById(@Path("id") id: Int): Call<ContactResource>

    //@POST("media-outlet/contacts")
    //fun create(@Body newObject: CreateContact): Call<ContactResource>
}