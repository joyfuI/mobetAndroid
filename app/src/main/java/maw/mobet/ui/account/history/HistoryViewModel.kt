package maw.mobet.ui.account.history

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.HistoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val _list = MutableLiveData<List<HistoryListItem>>().apply {
        loadData()
    }

    val list: LiveData<List<HistoryListItem>>
        get() = _list

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.historyList()
        dataCall.enqueue(object : Callback<List<HistoryItem>> {
            override fun onResponse(
                call: Call<List<HistoryItem>>, response: Response<List<HistoryItem>>
            ) {
                _list.value = createList(response.body() ?: emptyList())
            }

            override fun onFailure(call: Call<List<HistoryItem>>, t: Throwable) {
                Toast.makeText(
                    getApplication(),
                    getApplication<Application>().resources.getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun createList(data: List<HistoryItem>): List<HistoryListItem> {
        val list = ArrayList<HistoryListItem>()
        val dateTmp = Calendar.getInstance()
        dateTmp.set(0, 0, 0)
        var header: HistoryListHeaderItem? = null

        for (i in data) {
            val date = Calendar.getInstance()
            date.time = i.date

            if (
                date.get(Calendar.YEAR) != dateTmp.get(Calendar.YEAR) ||
                date.get(Calendar.MONTH) != dateTmp.get(Calendar.MONTH) ||
                date.get(Calendar.DATE) != dateTmp.get(Calendar.DATE)
            ) {
                dateTmp.time = i.date
                header = HistoryListHeaderItem(i.date)
                list.add(header)
            }
            header?.add(i.money)
            list.add(HistoryListDataItem(i.name, i.money))
        }
        return list
    }
}
