package maw.mobet.api

import java.util.*

data class CreategameData(
    val title: String,      // 경쟁전 제목
    val public: Boolean,    // 공개방 여부
    val greater: Int?,      // 참가조건 최소금액 (없으면 null)
    val less: Int?,         // 참가조건 최대금액 (없으면 null)
    val start: Date,        // 시작날짜 (형식: yyyy-MM-dd)
    val end: Date,          // 종료날짜 (형식: yyyy-MM-dd)
    val price: Int,         // 금액 (음수면 이하, 양수면 이상)
    val category: Int       // 카테고리
)
