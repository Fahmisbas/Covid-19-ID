package com.fahmisbas.covid19id.ui.infographics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.db.DatabaseCache
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.data.model.Infographics
import com.fahmisbas.covid19id.data.model.InfographicsData
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfrographicViewModel(application: Application) : BaseViewModel(application){

    private val apiService = ApiService()
    val infographics = MutableLiveData<List<Infographics>>()
    val error = MutableLiveData<Boolean>()


    override fun fetch() {
        launch {
            val infographicsCache =
                DatabaseCache(getApplication()).infographicsDao().getAllnfographics()
            if (infographicsCache.isEmpty()) {
                fetchFromEndpoint()
            } else {
                retrieved(infographicsCache)
            }
        }
    }

    private fun fetchFromEndpoint() {
        apiService.getInfographics().enqueue(object : Callback<InfographicsData> {
            override fun onResponse(
                call: Call<InfographicsData>,
                response: Response<InfographicsData>
            ) {
                if (response.isSuccessful) {
                    response.body()?.infographicList?.let { result ->
                        storeInfographicsLocally(result)
                    }
                }
            }

            override fun onFailure(call: Call<InfographicsData>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun storeInfographicsLocally(list: List<Infographics>) {
        launch {
            val dao = DatabaseCache(getApplication()).infographicsDao()
            dao.deleteInfographcs()
            val result = dao.instetInfographics(*list.toTypedArray())
            var i = 0
            while (i < result.size) {
                list[i].uuid = result[i].toInt()
                ++i
            }
            retrieved(list)
        }
    }

    private fun retrieved(list: List<Infographics>) {
        infographics.value = list
        error.value = false
    }
}