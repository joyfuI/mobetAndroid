package maw.mobet.api

import java.util.*

data class HistoryItem(
    val name: String,
    val category: Int,
    val money: Int,
    val date: Date
)
