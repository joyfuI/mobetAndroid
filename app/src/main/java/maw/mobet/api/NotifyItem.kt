package maw.mobet.api

data class NotifyItem(
    val id: Int,
    val gameId: Int,
    val title: String,
    val member: MemberItem
)
