package com.fahmisbas.covid19id.ui.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.Indonesia
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(application: Application): BaseViewModel(application) {

    private val apiService = ApiService()

    val indonesia = MutableLiveData<Indonesia>()
    val error = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromEndpoint()
    }

    private fun fetchFromEndpoint() {
        apiService.getIndonesia().enqueue(object :  Callback<Indonesia> {
            override fun onResponse(call: Call<Indonesia>, response: Response<Indonesia>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        retrieved(result)
                    }
                }
            }
            override fun onFailure(call: Call<Indonesia>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun retrieved(body : Indonesia) {
        indonesia.value = body
        error.value = false
    }
}