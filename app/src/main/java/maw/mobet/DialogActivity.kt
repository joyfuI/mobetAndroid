package maw.mobet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import maw.mobet.ui.game.GameViewModel
import splitties.toast.toast

class DialogActivity : AppCompatActivity() {
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            toast(R.string.access_error)
            finish()
            return
        }
        viewModel.loadData(id)
    }
}
