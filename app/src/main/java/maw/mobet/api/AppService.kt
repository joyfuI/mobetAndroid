package maw.mobet.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

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

    @GET("friend-list/")
    fun friend(): Call<List<MemberItem>>

    @POST("notification/")
    fun invite(@Body data: List<IdData>): Call<ResultItem>

    @POST("post.php")
    fun deleteGame(@Body data: IdData): Call<ResultItem>

    @POST("friend.json")
    fun rankList(): Call<List<MemberItem>>

    @POST("my.json")
    fun my(): Call<MyItem>

    @Multipart
    @POST("restore-img/")
    fun uploadImg(@Part file: MultipartBody.Part): Call<ResultItem>

    @POST("rank.json")
    fun personal(): Call<RankItem>
}
