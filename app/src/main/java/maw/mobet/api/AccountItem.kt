package maw.mobet.api

data class AccountItem(
    val history: List<HistoryItem>, // 거래내역 리스트
    val month: List<MonthItem>,     // 월별 거래내역 리스트 (6개월)
    val game: List<GameItem>        // 참여 중인 게임 리스트
)
