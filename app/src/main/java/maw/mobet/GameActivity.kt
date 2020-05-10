package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_game.*
import maw.mobet.api.GameItem
import maw.mobet.databinding.ActivityGameBinding
import maw.mobet.ui.game.GameViewModel
import splitties.activities.start
import splitties.toast.toast

class GameActivity : AppCompatActivity() {
    private lateinit var viewModel: GameViewModel
    private lateinit var info: GameItem

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
        }

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        viewModel.info.observe(this, Observer {
            info = it

            if (info.start) {
                // 경쟁전이 시작했으면 game2 액티비티 실행
                start<Game2Activity> {
                    putExtra("id", id)
                    putExtra("data", info)
                }
                finish()
            }

            binding.game = info
        })
        viewModel.loadData(id)
    }

    fun onClick(view: View) {
        when (view) {
            compete_btn -> {
            }
        }
    }
}
