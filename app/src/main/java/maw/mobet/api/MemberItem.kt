package maw.mobet.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberItem(
    @SerializedName("id") val id: Int,              // 사용자 id (서버 임의)
    @SerializedName("nick") val nick: String,       // 사용자 닉네임
    @SerializedName("imgUrl") val imgUrl: String,   // 프로필이미지 url
    @SerializedName("score") val score: Int,        // 점수
    @SerializedName("grade") val grade: String,     // 등급
    @SerializedName("rank") val rank: Int,          // 전체 순위
    @SerializedName("remain") val remain: Int?,     // 남은금액 (정보가 없으면 null)
    @SerializedName("place") val place: Int?        // 등수 (정보가 없으면 null)
) : Parcelable
