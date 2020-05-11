package maw.mobet.api

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberItem(
    val id: Int,
    val nick: String,
    val imgUrl: String
) : Parcelable
