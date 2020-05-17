package maw.mobet.invite

import maw.mobet.api.MemberItem

data class MemberItem2(val member: MemberItem) {
    var isChecked: Boolean = false
}
