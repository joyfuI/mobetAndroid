package maw.mobet.ui.rank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.MemberItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class RankViewModel : ViewModel() {
    private val _list = MutableLiveData<List<MemberItem>>().apply {
        loadData()
    }

    val list: LiveData<List<MemberItem>>
        get() = _list

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.rankList()
        dataCall.enqueue(object : Callback<List<MemberItem>> {
            override fun onResponse(
                call: Call<List<MemberItem>>, response: Response<List<MemberItem>>
            ) {
                _list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<MemberItem>>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
