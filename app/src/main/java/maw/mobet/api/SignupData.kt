package maw.mobet.api

data class SignupData(
    val email: String,
    val nick: String,
    val phone: String,
    var uid: String? = null
)
