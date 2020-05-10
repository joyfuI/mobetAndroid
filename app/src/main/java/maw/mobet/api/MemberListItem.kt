package maw.mobet.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberListItem(
    val id: Int,
    val name: String,
    val imgUrl: String
) : Parcelable
