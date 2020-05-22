package maw.mobet.api

import com.google.gson.annotations.SerializedName

data class AccountItem(
    @SerializedName("history") val history: List<HistoryItem>,  // 거래내역 리스트
    @SerializedName("month") val month: List<MonthItem>         // 월별 거래내역 리스트 (6개월)
)
