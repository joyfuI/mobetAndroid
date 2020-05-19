package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppService {
    @GET("login/")
    fun login(): Call<ResultItem>

    @POST("sign-up/")
    fun signup(@Body data: SignupData): Call<ResultItem>

    @POST("userid-check/")
    fun emailCheck(@Body data: String): Call<ResultItem>

    @POST("nickname-check/")
    fun nickCheck(@Body data: String): Call<ResultItem>

    @POST("phonenum-check/")
    fun phoneCheck(@Body data: String): Call<ResultItem>

    @POST("alarm-check/")
    fun notify(): Call<ResultItem>

    @POST("delete-alarm/")
    fun notifyRequest(@Body data: IdData): Call<ResultItem>

    @GET("game/")
    fun homeList(): Call<List<GameItem>>

    @GET("notification/")
    fun notifyList(): Call<List<NotifyItem>>

    @POST("account.json")
    fun account(): Call<AccountItem>

    @POST("game/")
    fun createGame(@Body data: CreategameData): Call<ResultItem>

    @POST("game-inform/")
    fun game(@Body data: IdData): Call<GameItem>

    @POST("participate/")
    fun joinGame(@Body data: IdData): Call<ResultItem>

    @POST("friend.json")
    fun friend(): Call<List<MemberItem>>

    @POST("notification/")
    fun invite(@Body data: List<MemberItem>): Call<ResultItem>

    @POST("post.php")
    fun deleteGame(@Body data: IdData): Call<ResultItem>

    @POST("rank.json")
    fun rankList(): Call<List<MemberItem>>

    @POST("my.json")
    fun my(): Call<MyItem>

    @POST("rank.json")
    fun personal(): Call<RankItem>
}
