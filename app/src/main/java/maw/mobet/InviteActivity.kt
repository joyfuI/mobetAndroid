package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_invite.*
import maw.mobet.api.MemberItem
import maw.mobet.api.ResultItem
import maw.mobet.invite.MemberItem2
import maw.mobet.invite.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.resources.txt
import splitties.toast.toast

class InviteActivity : AppCompatActivity() {
    private lateinit var list: List<MemberItem2>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)

        list_view.layoutManager = LinearLayoutManager(this)

        val service = RetrofitClient.getInstance()
        val dataCall = service.friend()
        dataCall.enqueue(object : Callback<List<MemberItem>> {
            override fun onResponse(
                call: Call<List<MemberItem>>, response: Response<List<MemberItem>>
            ) {
                list = createList(response.body()!!)
                list_view.adapter = MyAdapter(list)
            }

            override fun onFailure(call: Call<List<MemberItem>>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
                finish()
            }
        })
    }

    private fun createList(data: List<MemberItem>): List<MemberItem2> {
        val list = ArrayList<MemberItem2>()

        for (i in data) {
            list.add(MemberItem2(i))
        }
        return list
    }

    fun onClick(view: View) {
        when (view) {
            // 확인 버튼
            ok_btn -> {
                ok_btn.isClickable = false

                val inviteList = list.filter {
                    it.isChecked
                }.map {
                    MemberItem(it.id, it.nick, it.imgUrl)
                }

                val service = RetrofitClient.getInstance()
                val dataCall = service.invite(inviteList)
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        if (result?.code == 0) {
                            toast(R.string.invite_ok)
                            finish()
                            return
                        }
                        toast("${txt(R.string.error)} ${result?.code}")
                        ok_btn.isClickable = true
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                        ok_btn.isClickable = true
                    }
                })
            }
            // 취소 버튼
            cancel_btn -> {
                finish()
            }
        }
    }
}
