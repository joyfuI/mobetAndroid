package maw.mobet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.GameItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class HomeViewModel : ViewModel() {
    private val _list = MutableLiveData<List<GameItem>>().apply {
        loadData()
    }

    val list: LiveData<List<GameItem>>
        get() = _list

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.homeList()
        dataCall.enqueue(object : Callback<List<GameItem>> {
            override fun onResponse(
                call: Call<List<GameItem>>, response: Response<List<GameItem>>
            ) {
                _list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<GameItem>>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
