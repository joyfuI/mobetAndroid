package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import maw.mobet.api.GameItem
import maw.mobet.api.IdData
import maw.mobet.databinding.ActivityGameBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.appTxt
import splitties.toast.toast

class GameActivity : AppCompatActivity() {
    private lateinit var info: GameItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_game)
        val binding: ActivityGameBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_game
        )

        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            finish()
        }

        val service = RetrofitClient.getInstance()
        val dataCall = service.game(IdData(id, 0))
        dataCall.enqueue(object : Callback<GameItem> {
            override fun onResponse(
                call: Call<GameItem>, response: Response<GameItem>
            ) {
                info = response.body()!!
                binding.game = info
            }

            override fun onFailure(call: Call<GameItem>, t: Throwable) {
                toast("${appTxt(R.string.network_error)}\n${t.localizedMessage}")
                finish()
            }
        })
    }

    fun onClick(view: View) {
    }
}
