package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {
    @POST("sign-up/")
    fun signup(@Body data: SignupData): Call<ResultItem>

    @POST("userid-check/")
    fun emailCheck(@Body data: String): Call<ResultItem>

    @POST("nickname-check/")
    fun nickCheck(@Body data: String): Call<ResultItem>

    @POST("phonenum-check/")
    fun phoneCheck(@Body data: String): Call<ResultItem>

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

    @POST("friend.json")
    fun friend(): Call<List<MemberItem>>

    @POST("post.php")
    fun invite(@Body data: List<MemberItem>): Call<ResultItem>
}
