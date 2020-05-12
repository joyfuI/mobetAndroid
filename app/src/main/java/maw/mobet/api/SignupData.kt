package maw.mobet.api

import com.google.gson.annotations.SerializedName

data class SignupData(
    @SerializedName("USERID") val email: String,
    @SerializedName("NICKNAME") val nick: String,
    @SerializedName("PHONENUM") val phone: String,
    @SerializedName("USERUID") var uid: String? = null
)
