package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import maw.mobet.api.NotifyItem
import maw.mobet.notify.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.txt
import splitties.toast.toast

class RankActivity : AppCompatActivity(), MyAdapter.OnClickListener {
    private val list = MutableLiveData<List<NotifyItem>>().apply {
        loadData()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_personal)
    }

    private fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.notifyList()
        dataCall.enqueue(object : Callback<List<NotifyItem>> {
            override fun onResponse(
                call: Call<List<NotifyItem>>, response: Response<List<NotifyItem>>
            ) {
                list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<NotifyItem>>, t: Throwable) {
                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }


    override fun onClick(view: View, position: Int, delete: () -> Unit) {
        TODO("Not yet implemented")
    }
}