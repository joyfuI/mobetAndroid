package maw.mobet.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.AccountItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class AccountViewModel : ViewModel() {
    private val _list = MutableLiveData<AccountItem>().apply {
        loadData()
    }

    val list: LiveData<AccountItem>
        get() = _list

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.account()
        dataCall.enqueue(object : Callback<AccountItem> {
            override fun onResponse(
                call: Call<AccountItem>, response: Response<AccountItem>
            ) {
                _list.value = response.body()
            }

            override fun onFailure(call: Call<AccountItem>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
