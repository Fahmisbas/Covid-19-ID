package com.fahmisbas.covid19id.view.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.covid19id.model.ApiService
import com.fahmisbas.covid19id.model.ProvinceResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapViewModel : ViewModel() {

    private val apiService = ApiService()

    val province = MutableLiveData<ProvinceResult>()
    val error = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun fetch() {
        getDataFromRemote();
    }

    private fun getDataFromRemote() {
        apiService.getProvince().enqueue(object : Callback<ProvinceResult> {
            override fun onFailure(call: Call<ProvinceResult>, t: Throwable) {
                error.value = true
                errorMessage.value = t.message
            }

            override fun onResponse(
                call: Call<ProvinceResult>, response: Response<ProvinceResult>) {
                val body = response.body()
                province.value = body
                error.value = false
            }
        })
    }

}