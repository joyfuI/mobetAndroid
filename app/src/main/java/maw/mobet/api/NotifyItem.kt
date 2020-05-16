package maw.mobet.api

import com.google.gson.annotations.SerializedName

data class NotifyItem(
    @SerializedName("pk") val id: Int,                      // 알림 id
    @SerializedName("ROOMNAME") val gameId: Int,            // 게임 id
    @SerializedName("game_name") val title: String,         // 게임 제목
    @SerializedName("owner_profile") val member: MemberItem // 초대보낸 사람의 정보
)
