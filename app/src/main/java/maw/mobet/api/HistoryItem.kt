package maw.mobet.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class HistoryItem(
    @SerializedName("name") val name: String,       // 거래내역 이름
    @SerializedName("category") val category: Int,  // 카테고리
    @SerializedName("money") val money: Int,        // 지출 (양수)
    @SerializedName("date") val date: Date          // 날짜 (형식: yyyy-MM-dd'T'HH:mm:ss)
)
