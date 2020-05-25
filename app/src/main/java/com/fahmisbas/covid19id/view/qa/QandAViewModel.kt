package com.fahmisbas.covid19id.view.qa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.covid19id.model.ApiService
import com.fahmisbas.covid19id.model.QandA
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QandAViewModel : ViewModel() {

    private val apiService = ApiService()

    var qandA = MutableLiveData<List<QandA>>()
    var error = MutableLiveData<Boolean>()

    fun fecth() {
        getDataFromRemote()
    }

    private fun getDataFromRemote() {
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