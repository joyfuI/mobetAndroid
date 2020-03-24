package maw.mobet.api

data class HomeListItem(
    val name: String,
    val title: String,
    val category: Int,
    val price: Int,
    val startDate: String,
    val endDate: String,
    val imgUrl: String
)
