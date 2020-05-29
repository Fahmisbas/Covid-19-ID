package com.fahmisbas.covid19id.ui.qa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.covid19id.data.MythBuster
import com.fahmisbas.covid19id.data.QandA
import com.fahmisbas.covid19id.data.httprequest.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QandAViewModel : ViewModel() {

    private val apiService = ApiService()

    var qandA = MutableLiveData<List<QandA>>()
    var mythBuster = MutableLiveData<List<MythBuster>>()
    var error = MutableLiveData<Boolean>()

    fun fetch() {
        getQandADataFromRemote()
        getMythBusterFromRemote()
    }

    private fun getMythBusterFromRemote() {
        apiService.getMythBuster().enqueue(object : Callback<List<MythBuster>> {
            override fun onFailure(call: Call<List<MythBuster>>, t: Throwable) {
                error.value = true
            }

            override fun onResponse(
                call: Call<List<MythBuster>>,
                response: Response<List<MythBuster>>
            ) {
                error.value = false
                mythBuster.value = response.body()

            }

        })
    }

    private fun getQandADataFromRemote() {
        apiService.getQandA().enqueue(object : Callback<List<QandA>> {
            override fun onFailure(call: Call<List<QandA>>, t: Throwable) {
                error.value = true
            }

            override fun onResponse(call: Call<List<QandA>>, response: Response<List<QandA>>) {
                error.value = false
                qandA.value = response.body()
            }
        })
    }
}