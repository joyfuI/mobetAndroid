package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST("post.php")
    fun signup(@Body data: SignupData): Call<ResultItem>

    @POST("post.php")
    fun nickCheck(@Body data: NickData): Call<ResultItem>

    @POST("post.php")
    fun phoneCheck(@Body data: PhoneData): Call<ResultItem>

    @POST("notify.json")
    fun notify(): Call<ResultItem>

    @POST("post.php")
    fun notifyRequest(@Body data: IdData): Call<ResultItem>

    @POST("home.json")
    fun homeList(): Call<List<GameItem>>

    @POST("notifylist.json")
    fun notifyList(): Call<List<NotifyItem>>

    @POST("account.json")
    fun account(): Call<AccountItem>

    @POST("post.php")
    fun createGame(@Body data: CreategameData): Call<ResultItem>

    @POST("game.json")
    fun game(@Body data: IdData): Call<GameItem>

    @POST("post.php")
    fun joinGame(@Body data: IdData): Call<ResultItem>
}
