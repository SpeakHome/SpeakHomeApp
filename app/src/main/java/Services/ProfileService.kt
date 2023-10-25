package Services

import Models.ApiResponse
import Models.Profile.CreateProfile
import Models.Profile.ProfileResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ProfileService {
    @GET("security/profiles")
    fun getAll(): Call<ApiResponse<List<ProfileResource>>>

    @GET("security/profiles/{id}")
    fun getById(@Path("id") id: Int): Call<ProfileResource>

    @POST("security/profiles")
    fun create(@Body newObject: CreateProfile): Call<ProfileResource>
}
