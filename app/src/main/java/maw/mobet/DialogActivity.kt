package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.activity_dialog.grade_btn
import kotlinx.android.synthetic.main.activity_dialog.list_view
import kotlinx.android.synthetic.main.activity_rank_personal.*
import kotlinx.android.synthetic.main.fragment_rank.*
import kotlinx.android.synthetic.main.list_item_rank.*
import maw.mobet.api.GameItem
import maw.mobet.api.MyItem
import maw.mobet.api.NotifyItem
import maw.mobet.api.RankItem
import maw.mobet.notify.MyAdapter
import maw.mobet.ui.game.GameViewModel
import maw.mobet.ui.my.MyViewModel
import maw.mobet.ui.my.finish.DialogAdapter
import maw.mobet.ui.my.finish.PersonalViewModel
import maw.mobet.ui.rank.RankViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.resources.txt
import splitties.toast.toast
import java.util.*
import kotlin.math.absoluteValue

class DialogActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var rank : MyItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        list_view.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()
        val id = intent.getIntExtra("id", -1)
        if (id == -1) {
            toast(R.string.access_error)
            finish()
            return
        }
        val data = intent.getParcelableExtra<GameItem>("data")!!
        viewModel.loadData()
        viewModel.list.observe(this, Observer {
            rank = it
            list_view.adapter = DialogAdapter(data.members)
            grade_btn.text = rank.my.grade
            title_txt.text = data.title
            val backgroundArr = title_img.resources.obtainTypedArray(R.array.category_background)
            title_img.setImageResource(backgroundArr.getResourceId(data.category, 0))
            backgroundArr.recycle()
            val catedata = "[${data.category}]" +
                    "${data.startDate.toString("MM.DD")} ~ ${data.endDate.toString("MM.DD")}"
            cate_date_txt.text = catedata
            val amountdata = "사용금액 : ${data.members[id].remain} / " + "${data.price.absoluteValue}"
            title_bottom_txt.text = amountdata

        })
    }
}