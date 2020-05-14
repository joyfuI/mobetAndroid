package maw.mobet.api

import java.util.*

data class MonthItem(
    val month: Date,    // 월 (형식: yyyy-MM-01)
    val sum: Int        // 월 지출 총합
)
