package maw.mobet.ui.account.history

import java.util.*
import kotlin.math.absoluteValue

class HistoryListHeaderItem (
    val date: Date
) : HistoryListItem() {
    override fun getType() = TYPE_HEADER

    private var _sum = 0
    val sum: Int
        get() = _sum

    fun add(num: Int) {
        _sum += num.absoluteValue
    }
}
