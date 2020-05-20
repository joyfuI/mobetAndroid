package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import maw.mobet.api.NotifyItem
import maw.mobet.notify.MyAdapter
import maw.mobet.ui.game.GameViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.txt
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

    override fun onClick(view: View, position: Int, delete: () -> Unit) {

    }
}