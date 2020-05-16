package maw.mobet.api

import java.util.*

data class HistoryItem(
    val name: String,   // 거래내역 이름
    val category: Int,  // 카테고리
    val money: Int,     // 금액 (음수면 지출, 양수면 수입)
    val date: Date      // 날짜 (형식: yyyy-MM-dd'T'HH:mm:ss)
)
