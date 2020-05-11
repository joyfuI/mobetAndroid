package maw.mobet.api

data class AccountItem(
    val history: List<HistoryItem>,
    val month: List<MonthItem>,
    val game: List<GameItem>
)
