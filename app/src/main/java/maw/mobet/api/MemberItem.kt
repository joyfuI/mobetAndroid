package maw.mobet.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberItem(
    val id: Int,        // 사용자 id (서버 임의)
    val nick: String,   // 사용자 닉네임
    val imgUrl: String, // 프로필이미지 url
    val score: Int,     // 점수
    val grade: Int,     // 등급
    val rank: Int       // 전체 순위
) : Parcelable
