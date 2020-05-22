package maw.mobet.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class MonthItem(
    @SerializedName("month") val month: Date,   // 월 (형식: yyyy-MM-01)
    @SerializedName("sum") val sum: Int         // 월 지출 총합
)
