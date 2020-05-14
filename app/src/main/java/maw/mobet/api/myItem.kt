package maw.mobet.api

data class myItem(
    val my: MemberItem,             // 내 정보
    val playing: List<GameItem>,    // 진행중인 게임 리스트
    val ending: List<GameItem>      // 종료된 게임 리스트
)
