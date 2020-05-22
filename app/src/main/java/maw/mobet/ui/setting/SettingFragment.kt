package maw.mobet.ui.setting

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth
import maw.mobet.LoginActivity
import maw.mobet.R
import maw.mobet.RetrofitClient
import maw.mobet.api.ResultItem
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.cancelButton
import splitties.alertdialog.appcompat.messageResource
import splitties.alertdialog.appcompat.okButton
import splitties.fragments.start
import splitties.resources.str
import splitties.resources.txt
import splitties.toast.toast
import java.io.File
import java.io.FileOutputStream

class SettingFragment : PreferenceFragmentCompat() {
    private lateinit var file: File

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // 프로필 이미지 변경
        findPreference<Preference>("profile_img")?.setOnPreferenceClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(intent, 0)
            false
        }

        // 친구 추가
        findPreference<Preference>("friend_add")?.setOnPreferenceClickListener {
            val editText = EditText(requireContext())

            AlertDialog.Builder(requireContext())
                .setTitle(R.string.setting_friend_add)
                .setMessage(R.string.setting_friend_msg)
                .setView(editText)
                .apply {
                    cancelButton()
                    okButton {
                        if (editText.text.isEmpty()) {
                            toast(R.string.setting_friend_msg)
                        } else {
                            // 친구 추가
                            val service = RetrofitClient.getInstance()
                            val dataCall = service.friendAdd(editText.text.toString())
                            dataCall.enqueue(object : Callback<ResultItem> {
                                override fun onResponse(
                                    call: Call<ResultItem>, response: Response<ResultItem>
                                ) {
                                    val result = response.body()
                                    when (result?.code) {
                                        // 완료
                                        0 -> {
                                            toast(R.string.friend_add_ok)
                                        }
                                        // 없음
                                        1 -> {
                                            toast(R.string.friend_add_no)
                                        }
                                        // 오류
                                        else -> {
                                            toast("${txt(R.string.error)} ${result?.code}")
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                                    toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                                }
                            })
                        }
                    }
                }.show()
            false
        }

        // 로그아웃
        findPreference<Preference>("logout")?.setOnPreferenceClickListener {
            requireContext().alertDialog {
                messageResource = R.string.setting_logout_alert
                okButton {
                    // 로그아웃
                    FirebaseAuth.getInstance().signOut()
                    start<LoginActivity> {
                        putExtra("anim", false) // 애니메이션 x
                    }
                    requireActivity().finish()
                }
                cancelButton()
            }.show()
            false
        }

        file = File.createTempFile(str(R.string.app_name), null, requireActivity().cacheDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            if (resultCode == RESULT_OK && data != null) {
                val extension = requireActivity().contentResolver.getType(data.data!!)!!.split("/")[1]

                val inputStream = requireActivity().contentResolver.openInputStream(data.data!!)!!
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(inputStream.available())

                inputStream.read(buffer)
                outputStream.write(buffer)

                inputStream.close()
                outputStream.close()

                val service = RetrofitClient.getInstance()
                val fileBody = file.asRequestBody("image/*".toMediaType())
                val filePart = MultipartBody.Part.createFormData(
                    "upload",
                    "profile"/* + when (extension) {
                        "jpeg" -> ".jpg"
                        "png" -> ".png"
                        "gif" -> ".gif"
                        else -> {
                            toast(R.string.profile_img_extension)
                            return
                        }
                    }*/,
                    fileBody
                )
                val dataCall = service.uploadImg(filePart)
                dataCall.enqueue(object : Callback<ResultItem> {
                    override fun onResponse(
                        call: Call<ResultItem>, response: Response<ResultItem>
                    ) {
                        toast(R.string.profile_img_ok)
                    }

                    override fun onFailure(call: Call<ResultItem>, t: Throwable) {
                        toast("${txt(R.string.network_error)}\n${t.localizedMessage}")
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        file.delete()
    }
}
