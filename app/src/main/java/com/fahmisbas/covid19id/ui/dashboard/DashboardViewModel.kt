package com.fahmisbas.covid19id.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.covid19id.data.Indonesia
import com.fahmisbas.covid19id.data.httprequest.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel: ViewModel() {

    private val apiService = ApiService()

    val indonesia = MutableLiveData<Indonesia>()
    val error = MutableLiveData<Boolean>()

    fun refresh() {
        getDataFromRemote()
    }

    private fun getDataFromRemote() {
        apiService.getIndonesia().enqueue(object :  Callback<Indonesia> {
            override fun onResponse(call: Call<Indonesia>, response: Response<Indonesia>) {
                val body = response.body()
                indonesia.value = body
                error.value = false
            }
            override fun onFailure(call: Call<Indonesia>, t: Throwable) {
                error.value = true
            }
        })
    }
}