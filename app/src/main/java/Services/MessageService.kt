package Services

import Models.Message.CreateMessageResource
import Models.Message.MessageResource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageService {
    @POST("media-outlet/messages")
    fun create(@Body newObject: CreateMessageResource): Call<MessageResource>
}