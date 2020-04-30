package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notify.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import kotlinx.android.synthetic.main.list_item_notify.*
import maw.mobet.api.NotifyData
import maw.mobet.api.NotifyListItem
import maw.mobet.api.ResultItem
import maw.mobet.notify.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.txt
import splitties.toast.toast

class NotifyActivity : AppCompatActivity(), MyAdapter.OnClickListener {
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
            list_view.adapter = MyAdapter(it.toMutableList(), this)
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
                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }

    // 리스트 아이템 클릭
    override fun onClick(view: View, position: Int, delete: () -> Unit) {
        val item = view.tag as NotifyListItem
        val type = if (view.id == accept_btn.id) 1 else 0

        val service = RetrofitClient.getInstance()
        val dataCall = service.notifyRequest(NotifyData(item.id, type))
        dataCall.enqueue(object : Callback<ResultItem> {
            override fun onResponse(call: Call<ResultItem>, response: Response<ResultItem>) {
                val result = response.body()
                when (result?.code) {
                    // 사용가능
                    0 -> {
                        delete()
                    }
                    // 오류
                    else -> {
                        toast("${txt(R.string.error)} ${result?.code}")
                    }
                }
            }

            override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }
}
