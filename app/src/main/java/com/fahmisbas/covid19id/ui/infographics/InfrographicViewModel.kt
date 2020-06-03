package com.fahmisbas.covid19id.ui.infographics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.Infographics
import com.fahmisbas.covid19id.data.InfographicsResult
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfrographicViewModel(application: Application) : BaseViewModel(application){

    private val apiService = ApiService()
    val infographics = MutableLiveData<List<Infographics>>()
    val error = MutableLiveData<Boolean>()


    fun fetch() {
        fetchFromEndpoint()
    }

    private fun fetchFromEndpoint() {
        apiService.getInfographics().enqueue(object : Callback<InfographicsResult> {
            override fun onResponse(
                call: Call<InfographicsResult>,
                response: Response<InfographicsResult>
            ) {
                if (response.isSuccessful) {
                    response.body()?.infographicList?.let { result ->
                        infographics.value = result
                        error.value = false
                    }
                }
            }

            override fun onFailure(call: Call<InfographicsResult>, t: Throwable) {
                error.value = true
            }
        })
    }
}