package com.fahmisbas.covid19id.view.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.covid19id.model.ApiService
import com.fahmisbas.covid19id.model.Indonesia
import retrofit2.Callback

import retrofit2.Call
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