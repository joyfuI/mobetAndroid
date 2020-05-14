package maw.mobet.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppServiceTest : AppService {
    @GET("post.php")
    override fun login(): Call<ResultItem>

    @POST("post.php")
    override fun signup(@Body data: SignupData): Call<ResultItem>

    @POST("post.php")
    override fun emailCheck(@Body data: String): Call<ResultItem>

    @POST("post.php")
    override fun nickCheck(@Body data: String): Call<ResultItem>

    @POST("post.php")
    override fun phoneCheck(@Body data: String): Call<ResultItem>

    @POST("notify.json")
    override fun notify(): Call<ResultItem>

    @POST("post.php")
    override fun notifyRequest(@Body data: IdData): Call<ResultItem>

    @GET("home.json")
    override fun homeList(): Call<List<GameItem>>

    @POST("notifylist.json")
    override fun notifyList(): Call<List<NotifyItem>>

    @POST("account.json")
    override fun account(): Call<AccountItem>

    @POST("post.php")
    override fun createGame(@Body data: CreategameData): Call<ResultItem>

    @POST("game.json")
    override fun game(@Body data: IdData): Call<GameItem>

    @POST("post.php")
    override fun joinGame(@Body data: IdData): Call<ResultItem>

    @POST("friend.json")
    override fun friend(): Call<List<MemberItem>>

    @POST("post.php")
    override fun invite(@Body data: List<MemberItem>): Call<ResultItem>

    @POST("post.php")
    override fun deleteGame(@Body data: IdData): Call<ResultItem>

    @POST("rank.json")
    override fun rankList(): Call<List<MemberItem>>

    @POST("my.json")
    override fun my(): Call<myItem>
}
