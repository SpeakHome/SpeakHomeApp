package Services

import Models.DeviceIot.ButtonCommand
import retrofit2.Call
import retrofit2.http.GET

interface ButtonService {
    @GET("commands/buttons.json")
    fun getAll(): Call<List<ButtonCommand>>
}