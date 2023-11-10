package Services

import Models.ApiResponse
import Models.ContactMessageResource.ContactMessageResource
import Models.ContactMessageResource.CreateContactMessageResource
import Models.Device.DeviceResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactMessageService {
    @GET("media-outlet/contact-messages/by-profile/{profileId}")
    fun getMessagesByProfileId(@Path("profileId") profileId: Long): Call<ApiResponse<List<ContactMessageResource>>>

    @POST("media-outlet/contact-messages")
    fun create(@Body newObject: CreateContactMessageResource): Call<ContactMessageResource>
}