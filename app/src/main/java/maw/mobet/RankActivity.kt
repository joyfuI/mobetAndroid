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
import maw.mobet.ui.my.finish.PersonalViewModel

class RankActivity : AppCompatActivity() {

    private lateinit var viewModel: PersonalViewModel
    private lateinit var rank : RankItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_personal)
        viewModel = ViewModelProvider(this)[PersonalViewModel::class.java]

        viewModel.loadData()
        viewModel.rank.observe(this, Observer {
            rank = it
            progress_grade.progress = rank.nextRank.toInt()
            progress_rank.progress = rank.toppercent.toInt()
            grade_txt.text = rank.my.grade
            val text = "다음 등급까지 " + "${rank.nextRank.toString()} %"
            grade_next_txt.text = text
            val text2 = "상위 " + "${rank.nextRank.toString()} %"
            rank_txt.text = rank.my.rank.toString()
            top_rank_txt.text = text2
            progressbar_win.progress = rank.toppercent.toInt()
            progressbar_first.progress = rank.first.toInt()
            progressbar_play.progress = rank.playtimes.toInt()

        })
    }
}
