package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_friend.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.IdData
import maw.mobet.api.MemberItem
import maw.mobet.api.ResultItem
import maw.mobet.friend.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.txt
import splitties.toast.toast

class FriendActivity : AppCompatActivity(), MyAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private val list = MutableLiveData<List<MemberItem>>().apply {
        loadData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)

        // 액션바
        changeTitle(app_title_txt, getString(R.string.setting_friend))
        noti_img.visibility = View.GONE

        list_view.layoutManager = LinearLayoutManager(this)
        list.observe(this, Observer {
            list_view.adapter = MyAdapter(it.toMutableList(), this)
            swipe_l.isRefreshing = false
        })

        swipe_l.setOnRefreshListener(this)
    }

    private fun loadData() {
        val service = RetrofitClient.getInstance()
        val dataCall = service.friend()
        dataCall.enqueue(object : Callback<List<MemberItem>> {
            override fun onResponse(
                call: Call<List<MemberItem>>, response: Response<List<MemberItem>>
            ) {
                list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<MemberItem>>, t: Throwable) {
                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
            }
        })
    }

    // 리스트 아이템 클릭
    override fun onItemClick(view: View, position: Int, delete: () -> Unit) {
        view.isClickable = false

        val item = view.tag as MemberItem

        val service = RetrofitClient.getInstance()
        val dataCall = service.friendDelete(IdData(item.id, 0))
        dataCall.enqueue(object : Callback<ResultItem> {
            override fun onResponse(call: Call<ResultItem>, response: Response<ResultItem>) {
                val result = response.body()
                when (result?.code) {
                    // 성공
                    0 -> {
                        delete()
                        view.isClickable = true
                    }
                    // 오류
                    else -> {
                        toast("${txt(R.string.error)} ${result?.code}")
                        view.isClickable = true
                    }
                }
            }

            override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                view.isClickable = true
            }
        })
    }

    // 당겨서 새로고침
    override fun onRefresh() {
        loadData()
    }
}
