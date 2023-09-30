package Services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BaseService<T> {
    @GET("{endpoint}")
    fun getAll(@Path("endpoint") endpoint: String): Call<List<T>>

    @GET("{endpoint}/{id}")
    fun getById(@Path("endpoint") endpoint: String, @Path("id") id: Int): Call<T>

    @POST("{endpoint}")
    fun create(@Path("endpoint") endpoint: String, @Body newObject: T): Call<T>

    @PUT("{endpoint}/{id}")
    fun update(@Path("endpoint") endpoint: String, @Path("id") id: Int, @Body updatedObject: T): Call<T>

    @DELETE("{endpoint}/{id}")
    fun delete(@Path("endpoint") endpoint: String, @Path("id") id: Int): Call<Void>
}
