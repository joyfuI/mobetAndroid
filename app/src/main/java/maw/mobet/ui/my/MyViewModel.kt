package maw.mobet.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.MyItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class MyViewModel : ViewModel() {
    private val _list = MutableLiveData<MyItem>().apply {
        loadData()
    }

    val list: LiveData<MyItem>
        get() = _list

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.my()
        dataCall.enqueue(object : Callback<MyItem> {
            override fun onResponse(
                call: Call<MyItem>, response: Response<MyItem>
            ) {
                _list.value = response.body()
            }

            override fun onFailure(call: Call<MyItem>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
