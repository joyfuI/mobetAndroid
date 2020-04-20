package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @POST("login.json")
    fun login(@Body data: LoginData): Call<LoginItem>

    @POST("post.php")
    fun signup(@Body data: SignupData): Call<ResultItem>

    @GET("homelist.json")
    fun homeList(): Call<List<HomeListItem>>

    @GET("historylist.json")
    fun historyList(): Call<List<HistoryItem>>

    @POST("post.php")
    fun createGame(@Body data: CreategameData): Call<ResultItem>
}
