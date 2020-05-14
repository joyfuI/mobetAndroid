package maw.mobet.api

data class NotifyItem(
    val id: Int,            // 알림 id
    val gameId: Int,        // 게임 id
    val title: String,      // 게임 제목
    val member: MemberItem  // 초대보낸 사람의 정보
)
