package maw.mobet.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GameItem(
    val id: Int,
    val admin: Boolean,
    val compete: Boolean,
    val start: Boolean,
    val title: String,
    val name: String,
    val public: Boolean,
    val greater: Int?,
    val less: Int?,
    val startDate: Date,
    val endDate: Date,
    val price: Int,
    val category: Int,
    val members: List<MemberListItem>
) : Parcelable
