package maw.mobet.ui.setting

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import maw.mobet.LoginActivity
import maw.mobet.R
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.cancelButton
import splitties.alertdialog.appcompat.messageResource
import splitties.alertdialog.appcompat.okButton
import splitties.fragments.start

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        findPreference<Preference>("logout")?.setOnPreferenceClickListener {
            requireContext().alertDialog {
                messageResource = R.string.setting_logout_alert
                okButton {
                    // 로그아웃
                    FirebaseAuth.getInstance().signOut()
                    start<LoginActivity> {
                        // 애니메이션 x
                        putExtra("anim", false)
                    }
                    requireActivity().finish()
                }
                cancelButton()
            }.show()
            false
        }
    }
}
