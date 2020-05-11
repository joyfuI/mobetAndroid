package maw.mobet.api

import retrofit2.http.Query

data class SignupData(
    @Query("USERID") val email: String,
    @Query("NICKNAME") val nick: String,
    @Query("PHONENUM") val phone: String,
    @Query("USERUID") var uid: String? = null
)
