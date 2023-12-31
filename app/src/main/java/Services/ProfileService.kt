package Services

import Models.ApiResponse
import Models.Profile.CreateProfileResource
import Models.Profile.ProfileResource
import Models.Requests.LoginRequest
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

    @GET("security/profiles/by-userName/{userName}")
    fun getByUserName(@Path("userName") userName: String): Call<ProfileResource>

    @POST("security/profiles")
    fun create(@Body newObject: CreateProfileResource): Call<ProfileResource>

    @POST("security/profiles/login")
    fun login(@Body request: LoginRequest): Call<ProfileResource>

}
