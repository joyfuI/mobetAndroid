package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @POST("post.php")
    fun signup(@Body data: SignupData): Call<ResultItem>

    @POST("post.php")
    fun nickCheck(@Body data: NickData): Call<ResultItem>

    @POST("post.php")
    fun phoneCheck(@Body data: PhoneData): Call<ResultItem>

    @GET("notify.json")
    fun notify(): Call<ResultItem>

    @GET("homelist.json")
    fun homeList(): Call<List<HomeListItem>>

    @GET("notifylist.json")
    fun notifyList(): Call<List<NotifyListItem>>

    @GET("historylist.json")
    fun historyList(): Call<List<HistoryItem>>

    @POST("post.php")
    fun createGame(@Body data: CreategameData): Call<ResultItem>
}
