package maw.mobet.api

data class RankItem(
    val my: MemberItem,             // 내 정보
    val nextRank: Double,
    val toppercent : Double,
    val wins : Double,
    val first : Double,
    val playtimes : Double
)