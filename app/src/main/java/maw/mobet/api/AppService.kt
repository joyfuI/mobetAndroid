package maw.mobet.api

import retrofit2.Call
import retrofit2.http.GET

interface AppService {
    @GET("homelist.json")
    fun homeList(): Call<List<HomeListItem>>
}
