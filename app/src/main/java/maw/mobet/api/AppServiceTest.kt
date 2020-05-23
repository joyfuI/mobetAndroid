package maw.mobet.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface AppServiceTest : AppService {
    @GET("notify.json")
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

    @GET("notifylist.json")
    override fun notifyList(): Call<List<NotifyItem>>

    @GET("account.json")
    override fun account(): Call<AccountItem>

    @POST("post.php")
    override fun createGame(@Body data: CreategameData): Call<ResultItem>

    @POST("game.json")
    override fun game(@Body data: IdData): Call<GameItem>

    @POST("post.php")
    override fun joinGame(@Body data: IdData): Call<ResultItem>

    @GET("friend.json")
    override fun friend(): Call<List<MemberItem>>

    @POST("post.php")
    override fun invite(@Body data: List<IdData>): Call<ResultItem>

    @POST("post.php")
    override fun deleteGame(@Body data: IdData): Call<ResultItem>

    @GET("friend.json")
    override fun rankList(): Call<List<MemberItem>>

    @GET("my.json")
    override fun my(): Call<MyItem>

    @Multipart
    @POST("upload.php")
    override fun uploadImg(@Part file: MultipartBody.Part): Call<ResultItem>

    @POST("rank.json")
    override fun personal(@Body data: IdData): Call<RankItem>

    @POST("post.php")
    override fun friendAdd(@Body data: String): Call<ResultItem>

    @POST("post.php")
    override fun friendDelete(@Body data: IdData): Call<ResultItem>
}
