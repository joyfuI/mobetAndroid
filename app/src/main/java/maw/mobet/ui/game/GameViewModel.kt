package maw.mobet.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.GameItem
import maw.mobet.api.IdData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class GameViewModel : ViewModel() {
    private val _info = MutableLiveData<GameItem>()

    val info: LiveData<GameItem>
        get() = _info

    fun loadData(id: Int) {
        val service = RetrofitClient.getInstance()
        val dataCall = service.game(IdData(id, 0))
        dataCall.enqueue(object : Callback<GameItem> {
            override fun onResponse(
                call: Call<GameItem>, response: Response<GameItem>
            ) {
                _info.value = response.body()
            }

            override fun onFailure(call: Call<GameItem>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }

    fun loadData(data: GameItem) {
        _info.value = data
    }
}
