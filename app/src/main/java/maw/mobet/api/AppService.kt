package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @GET("homelist.json")
    fun homeList(): Call<List<HomeListItem>>

    @GET("historylist.json")
    fun historyList(): Call<List<HistoryItem>>

    @POST("post.php")
    fun createGame(@Body data: CreategameData): Call<ResultItem>
}
