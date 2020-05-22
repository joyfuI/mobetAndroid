package maw.mobet.ui.my.finish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.RankItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class PersonalViewModel : ViewModel() {
    private val _rank = MutableLiveData<RankItem>().apply {
        loadData()
    }

    val rank: LiveData<RankItem>
    get() = _rank

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.personal()
        dataCall.enqueue(object : Callback<RankItem> {
            override fun onResponse(
                call: Call<RankItem>, response: Response<RankItem>
            ) {
                _rank.value = response.body() ?: return
            }

            override fun onFailure(call: Call<RankItem>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
