package maw.mobet.api

import com.google.gson.annotations.SerializedName

data class SignupData(
    @SerializedName("USERID") val email: String,        // 이메일 주소
    @SerializedName("NICKNAME") val nick: String,       // 닉네임
    @SerializedName("PHONENUM") val phone: String,      // 전화번호
    @SerializedName("USERUID") var uid: String? = null  // 계정 uid
)
