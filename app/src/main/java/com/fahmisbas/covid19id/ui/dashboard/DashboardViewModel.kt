package com.fahmisbas.covid19id.ui.dashboard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.data.model.IndonesiaData
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(application: Application): BaseViewModel(application) {

    private val apiService = ApiService()

    val indonesia = MutableLiveData<IndonesiaData>()
    val error = MutableLiveData<Boolean>()

    override fun fetch() {
        fetchFromEndpoint()
    }

    private fun fetchFromEndpoint() {
        apiService.getIndonesia().enqueue(object : Callback<IndonesiaData> {
            override fun onResponse(call: Call<IndonesiaData>, response: Response<IndonesiaData>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        retrieved(result)
                    }
                }
            }
            override fun onFailure(call: Call<IndonesiaData>, t: Throwable) {
                error.value = true
            }
        })
    }

    fun retrieved(body: IndonesiaData) {
        indonesia.value = body
        error.value = false
    }
}