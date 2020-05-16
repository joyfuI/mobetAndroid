package maw.mobet.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GameItem(
    @SerializedName("pk") val id: Int,                          // 게임 id (서버 임의)
    @SerializedName("is_admin") val isAdmin: Boolean,           // 방장 여부
    @SerializedName("compete") val compete: Boolean,            // 참가 여부
    @SerializedName("is_start") val start: Boolean,             // 게임시작 여부
    @SerializedName("ROOMNAME") val title: String,              // 게임 제목
    @SerializedName("owner_profile") val admin: MemberItem,     // 방장 정보
    @SerializedName("OPEN") val public: Boolean,                // 공개방 여부
    @SerializedName("RECENTPAYAVGMIN") val greater: Int?,       // 참가조건 최소금액 (미지정시 null)
    @SerializedName("RECENTPAYAVGMAX") val less: Int?,          // 참가조건 최대금액 (미지정시 null)
    @SerializedName("STARTDATE") val startDate: Date,           // 시작날짜 (형식: yyyy-MM-dd)
    @SerializedName("DUEDATE") val endDate: Date,               // 종료날짜 (형식: yyyy-MM-dd)
    @SerializedName("SETTINGMONEY") val price: Int,             // 금액 (음수면 이하, 양수면 이상)
    @SerializedName("CATEGORYCODE") val category: Int,          // 카테고리
    @SerializedName("members") val members: List<MemberItem>    // 참가자 정보 (리스트)
) : Parcelable
