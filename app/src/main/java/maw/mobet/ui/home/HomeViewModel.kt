package maw.mobet.ui.home

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import maw.mobet.api.AppService
import maw.mobet.api.HomeListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _list = MutableLiveData<List<HomeListItem>>().apply {
        loadData()
    }

    val list: LiveData<List<HomeListItem>>
        get() = _list

    fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ljm.wo.tc/test/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AppService::class.java)
        val dataCall = service.homeList()
        dataCall.enqueue(object : Callback<List<HomeListItem>> {
            override fun onResponse(
                call: Call<List<HomeListItem>>, response: Response<List<HomeListItem>>
            ) {
                _list.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<HomeListItem>>, t: Throwable) {
                Log.d("joyfuI", t.toString())
                Toast.makeText(getApplication(), "네트워크 오류!!!", Toast.LENGTH_LONG).show()
            }
        })
    }
}
