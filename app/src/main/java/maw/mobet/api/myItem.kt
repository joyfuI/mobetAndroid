package maw.mobet.api

data class myItem(
    val my: MemberItem,
    val playing: List<GameItem>,
    val ending: List<GameItem>
)
