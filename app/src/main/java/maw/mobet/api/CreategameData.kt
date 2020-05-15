package maw.mobet.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class CreategameData(
    @SerializedName("ROOMNAME") val title: String,          // 경쟁전 제목
    @SerializedName("OPEN") val public: Boolean,            // 공개방 여부
    @SerializedName("RECENTPAYAVGMIN") val greater: Int?,   // 참가조건 최소금액 (없으면 null)
    @SerializedName("RECENTPAYAVGMAX") val less: Int?,      // 참가조건 최대금액 (없으면 null)
    @SerializedName("STARTDATE") val start: Date,           // 시작날짜 (형식: yyyy-MM-dd)
    @SerializedName("DUEDATE") val end: Date,               // 종료날짜 (형식: yyyy-MM-dd)
    @SerializedName("SETTINGMONEY") val price: Int,         // 금액 (음수면 이하, 양수면 이상)
    @SerializedName("CATEGORYCODE") val category: Int       // 카테고리
)
