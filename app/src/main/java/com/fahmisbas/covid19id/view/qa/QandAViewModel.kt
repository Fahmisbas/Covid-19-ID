package com.fahmisbas.covid19id.view.qa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fahmisbas.covid19id.model.ApiService
import com.fahmisbas.covid19id.model.QandA
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QandAViewModel : ViewModel(){

    private val apiService = ApiService()

    var qandA = MutableLiveData<QandA>()

    fun fecth(){
        getDataFromRemote()
    }

    private fun getDataFromRemote() {

    }

}