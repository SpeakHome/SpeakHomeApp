package Services

import Models.DeviceIot.SeekBarCommand
import retrofit2.Call
import retrofit2.http.GET

interface SeekBarService {
    @GET("commands/seekBars.json")
    fun getAll(): Call<List<SeekBarCommand>>
}