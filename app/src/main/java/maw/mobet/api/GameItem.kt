package maw.mobet.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GameItem(
    val id: Int,                    // 게임 id (서버 임의)
    val isAdmin: Boolean,           // 방장 여부
    val compete: Boolean,           // 참가 여부
    val start: Boolean,             // 게임시작 여부
    val title: String,              // 게임 제목
    val admin: MemberItem,          // 방장 정보
    val public: Boolean,            // 공개방 여부
    val greater: Int?,              // 참가조건 최소금액 (미지정시 null)
    val less: Int?,                 // 참가조건 최대금액 (미지정시 null)
    val startDate: Date,            // 시작날짜 (형식: yyyy-MM-dd)
    val endDate: Date,              // 종료날짜 (형식: yyyy-MM-dd)
    val price: Int,                 // 금액 (음수면 이하, 양수면 이상)
    val category: Int,              // 카테고리
    val members: List<MemberItem>,  // 참가자 정보 (리스트)
    val remain: Int?,               // 남은금액 (정보가 없으면 null)
    val place: Int?                 // 등수 (정보가 없으면 null)
) : Parcelable
