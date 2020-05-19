package maw.mobet

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_rank_personal.*
import maw.mobet.api.RankItem
import maw.mobet.notify.MyAdapter
import maw.mobet.ui.my.finish.PersonalViewModel
import splitties.activities.start

class RankActivity : AppCompatActivity(), MyAdapter.OnClickListener {
    private lateinit var viewModel: PersonalViewModel
    private lateinit var rank : RankItem

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_personal)
        viewModel = ViewModelProvider(this)[PersonalViewModel::class.java]

        viewModel.loadData()
        viewModel.rank.observe(this, Observer {
            rank = it
            progress_grade.setProgress(rank.nextRank.toInt(),true)
            progress_rank.setProgress(rank.toppercent.toInt(),true)
            grade_txt.text = rank.my.grade
            val text = "다음 등급까지 " + "${rank.nextRank.toString()} %"
            grade_next_txt.text = text
            val text2 = "상위 " + "${rank.nextRank.toString()} %"
            rank_txt.text = rank.my.rank.toString()
            top_rank_txt.text = text2
            progressbar_win.setProgress(rank.toppercent.toInt(), true)
            progressbar_first.setProgress(rank.first.toInt(), true)
            progressbar_play.setProgress(rank.playtimes.toInt(), true)

        })
    }
    override fun onClick(view: View, position: Int, delete: () -> Unit) {
    }
}