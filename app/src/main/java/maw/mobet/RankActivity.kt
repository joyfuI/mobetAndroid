package maw.mobet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_rank_personal.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import maw.mobet.api.RankItem
import maw.mobet.ui.my.finish.PersonalViewModel
import splitties.activities.start
import splitties.resources.appStr

class RankActivity : AppCompatActivity() {
    private lateinit var viewModel: PersonalViewModel
    private lateinit var rank : RankItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank_personal)
        viewModel = ViewModelProvider(this)[PersonalViewModel::class.java]

        // 액션바
        back_img.visibility = View.VISIBLE
        back_img.setOnClickListener {
            finish()
        }
        noti_img.setOnClickListener {
            start<NotifyActivity>()
        }

        viewModel.loadData()
        viewModel.rank.observe(this, Observer {
            rank = it

            var title = appStr(R.string.personal_title)
            title = title.replace("{0}", rank.my.nick)
            changeTitle(app_title_txt, title)

            progress_grade.progress = rank.nextRank.toInt()
            progress_rank.progress = rank.toppercent.toInt()
            grade_txt.text = rank.my.grade
            val text = "다음 등급까지 " + "${rank.nextRank} %"
            grade_next_txt.text = text
            val text2 = "상위 " + "${rank.nextRank} %"
            rank_txt.text = rank.my.rank.toString()
            top_rank_txt.text = text2
            progressbar_win.progress = rank.toppercent.toInt()
            progressbar_first.progress = rank.first.toInt()
            progressbar_play.progress = rank.playtimes.toInt()

        })
    }
}
