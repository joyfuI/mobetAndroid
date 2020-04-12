package maw.mobet.api

import java.util.*

data class CreategameData(
    val title: String,
    val public: Boolean,
    val greater: Int?,
    val less: Int?,
    val start: Date,
    val end: Date,
    val price: Int,
    val category: Int
)
