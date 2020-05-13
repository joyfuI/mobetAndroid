package maw.mobet.invite

import maw.mobet.api.MemberItem

class MemberItem2(data: MemberItem) {
    val id: Int = data.id
    val nick: String = data.nick
    val imgUrl: String = data.imgUrl
    var isChecked: Boolean = false
}
