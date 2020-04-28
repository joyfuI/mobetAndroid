package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notify.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.NotifyListItem
import maw.mobet.notify.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class NotifyActivity : AppCompatActivity() {
    private val list = MutableLiveData<List<NotifyListItem>>().apply {
        loadData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)

        // 액션바
        noti_img.visibility = View.GONE

        list_view.layoutManager = LinearLayoutManager(this)
        list.observe(this, Observer {
            list_view.adapter = MyAdapter(it)
        })
    }

    fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.notifyList()
        dataCall.enqueue(object : Callback<List<NotifyListItem>> {
            override fun onResponse(
                call: Call<List<NotifyListItem>>, response: Response<List<NotifyListItem>>
            ) {
                list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<NotifyListItem>>, t: Throwable) {
                toast("${resources.getString(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
