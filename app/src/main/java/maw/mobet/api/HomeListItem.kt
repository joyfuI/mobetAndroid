package maw.mobet.api

import java.util.*

data class HomeListItem(
    val name: String,
    val title: String,
    val category: Int,
    val price: Int,
    val startDate: Date,
    val endDate: Date,
    val imgUrl: String
)
