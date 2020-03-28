package maw.mobet.ui.account.history

class HistoryListDataItem(
    val name: String,
    val money: Int
) : HistoryListItem() {
    override fun getType() = TYPE_DATA
}
