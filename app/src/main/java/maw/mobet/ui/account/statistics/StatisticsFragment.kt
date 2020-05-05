package maw.mobet.ui.account.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_account_statistics.*
import maw.mobet.R
import maw.mobet.ui.account.AccountFragment
import maw.mobet.ui.account.AccountViewModel
import java.util.*

class StatisticsFragment : Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = StatisticsFragment()
    }

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(activity ?: this)[AccountViewModel::class.java]
        return inflater.inflate(R.layout.fragment_account_statistics, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        test.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            test -> {
                val cal = Calendar.getInstance().apply {
                    set(2020, 1 - 1, 10)
                }
                (parentFragment as AccountFragment).selectDate(cal.time)
            }
        }
    }
}
