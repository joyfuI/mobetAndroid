package maw.mobet.api

data class MyItem(
    val my: MemberItem,
    val playing: List<GameItem>,
    val ending: List<GameItem>
)
