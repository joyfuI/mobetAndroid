package maw.mobet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.game_info.*
import maw.mobet.api.GameItem
import maw.mobet.api.IdData
import maw.mobet.api.ResultItem
import maw.mobet.databinding.ActivityGameBinding
import maw.mobet.ui.game.GameViewModel
import maw.mobet.ui.game.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.activities.start
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.cancelButton
import splitties.alertdialog.appcompat.messageResource
import splitties.alertdialog.appcompat.okButton
import splitties.resources.txt
import splitties.toast.toast

class GameActivity : AppCompatActivity() {
    private lateinit var viewModel: GameViewModel
    private lateinit var info: GameItem
    private var isClickable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_game)
        val binding: ActivityGameBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_game
        )

        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            toast(R.string.access_error)
            finish()
            return
        }

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.info.observe(this, Observer {
            info = it

            if (info.start) {
                viewModel.info.removeObservers(this)
                // 경쟁전이 시작했으면 game2 액티비티 실행
                start<Game2Activity> {
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    putExtra("id", info.id)
                    putExtra("data", info)
                }
                finish()
            }

            list_view.adapter = MyAdapter(info.members)
            binding.game = info
        })
        val data = intent.getParcelableExtra<GameItem>("data")
        if (data != null) {
            viewModel.loadData(data)
        }

        list_view.layoutManager = GridLayoutManager(this, 3)
        viewModel.loadData(id)
    }

    fun onClick(view: View) {
        if (!isClickable) {
            return
        }
        when (view) {
            // 경잰전 참여/나가기
            compete_btn -> {
                isClickable = false
                val type = if (info.compete) 1 else 0
                val service = RetrofitClient.getInstance()
                val dataCall = service.joinGame(IdData(info.id, type))
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        val result = response.body()
                        if (result?.code == 0) {
                            alertDialog {
                                messageResource = if (info.compete) {
                                    // 나가기
                                    R.string.game_compete_out_alert
                                } else {
                                    // 참가
                                    R.string.game_compete_alert
                                }
                                okButton()
                            }.show()
                            viewModel.loadData(info.id)
                            isClickable = true
                            return
                        }
                        toast("${txt(R.string.error)} ${result?.code}")
                        isClickable = true
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                        isClickable = true
                    }
                })
            }
            // 친구초대
            invite_btn -> {
                val intent = Intent(this, InviteActivity::class.java)
                intent.putExtra("id", info.id)
                startActivityForResult(intent, 0)
            }
            // 경쟁전 삭제
            delete_btn -> {
                alertDialog {
                    messageResource = R.string.game_delete_alert
                    okButton {
                        isClickable = false
                        val service = RetrofitClient.getInstance()
                        val dataCall = service.deleteGame(IdData(info.id, 0))
                        dataCall.enqueue(object : Callback<ResultItem> {
                            override fun onResponse(
                                call: Call<ResultItem>, response: Response<ResultItem>
                            ) {
                                val result = response.body()
                                if (result?.code == 0) {
                                    finish()
                                    return
                                }
                                toast("${txt(R.string.error)} ${result?.code}")
                                isClickable = true
                            }

                            override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                                toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                                isClickable = true
                            }
                        })
                    }
                    cancelButton()
                }.show()
            }
        }
    }
}
