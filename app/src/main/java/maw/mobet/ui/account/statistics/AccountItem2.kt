package maw.mobet.ui.account.statistics

import maw.mobet.api.AccountItem

data class AccountItem2(
    var position: Int,
    val account: AccountItem,
    val max: Int,
    val avg: Int
)
