package com.fahmisbas.covid19id.ui.qa

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.MythBuster
import com.fahmisbas.covid19id.data.QandA
import com.fahmisbas.covid19id.data.db.DatabaseCache
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QandAViewModel(application: Application) : BaseViewModel(application) {

    private val apiService = ApiService()

    var qandA = MutableLiveData<List<QandA>>()
    var mythBuster = MutableLiveData<List<MythBuster>>()
    var error = MutableLiveData<Boolean>()

    fun fetch() {
        launch {

            val qandACache = DatabaseCache(getApplication()).qandADao().getQandA()
            val mythBusterCache = DatabaseCache(getApplication()).mythBusterDao().getMythBuster()

            if (qandACache.isNullOrEmpty() || mythBusterCache.isNullOrEmpty()) {
                fetchQandADataFromEndpoint()
                fetchMythBusterFromEndpoint()
            } else {
                qandARetrieved(qandACache)
                mythBusterRetrieved(mythBusterCache)
            }
        }
    }

    private fun fetchMythBusterFromEndpoint() {
        apiService.getMythBuster().enqueue(object : Callback<List<MythBuster>> {
            override fun onResponse(call: Call<List<MythBuster>>, response: Response<List<MythBuster>>) {
                storeMythBusterLocally(response.body())
            }
            override fun onFailure(call: Call<List<MythBuster>>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun fetchQandADataFromEndpoint() {
        apiService.getQandA().enqueue(object : Callback<List<QandA>> {
            override fun onResponse(call: Call<List<QandA>>, response: Response<List<QandA>>) {
                storeQandALocally(response.body())
            }
            override fun onFailure(call: Call<List<QandA>>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun storeMythBusterLocally(list: List<MythBuster>?) {
        launch {
            val dao = DatabaseCache(getApplication()).mythBusterDao()
            dao.deleteMythBuster()
            dao.insertMythBuster(*list!!.toTypedArray())
        }
        mythBusterRetrieved(list)
    }

    private fun storeQandALocally(list: List<QandA>?) {
        launch {
            val dao = DatabaseCache(getApplication()).qandADao()
            dao.deleteQandA()
            dao.insertQandA(*list!!.toTypedArray())
        }
        qandARetrieved(list!!)
    }

    private fun mythBusterRetrieved(list: List<MythBuster>?) {
        mythBuster.value = list
        error.value = false
    }

    private fun qandARetrieved(list: List<QandA>) {
        qandA.value = list
        error.value = false
    }
}