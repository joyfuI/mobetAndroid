package maw.mobet.ui.account.history

class HistoryListDataItem(
    val category: Int,
    val name: String,
    val money: Int
) : HistoryListItem() {
    override fun getType() = TYPE_DATA
}
