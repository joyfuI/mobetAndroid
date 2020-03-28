package maw.mobet.ui.account.history

import java.util.*

class HistoryListHeaderItem (
    val date: Date,
    var plus: Int = 0,
    var minus: Int = 0
) : HistoryListItem() {
    override fun getType() = TYPE_HEADER

    fun add(num: Int) {
        if (num > 0) {
            plus += num
        } else {
            minus += num
        }
    }
}
