package com.fahmisbas.covid19id.ui.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.ProvinceCases
import com.fahmisbas.covid19id.data.ProvinceLocation
import com.fahmisbas.covid19id.data.ProvinceLocationResult
import com.fahmisbas.covid19id.data.ProvinceResult
import com.fahmisbas.covid19id.data.db.DatabaseCache
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import com.fahmisbas.covid19id.util.SharedPreferenceHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapViewModel(application: Application) : BaseViewModel(application) {


    val provinceCases = MutableLiveData<List<ProvinceCases>>()
    val locations = MutableLiveData<List<ProvinceLocation>>()
    val error = MutableLiveData<Boolean>()


    private val apiService = ApiService()

    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun fetch() {
        checkCacheDuration()
        fetchProvinceLocationFromEndPoint()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchProvinceCasesFromDatabase()
            fetchProvinceLocationFromDatabase()
        } else {
            fetchProvinceCasesFromEndpoint()
            fetchProvinceLocationFromEndPoint()
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()
        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: 5 * 60
            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    private fun fetchProvinceLocationFromDatabase() {}
    private fun fetchProvinceCasesFromDatabase() {
        launch {
            val province = DatabaseCache(getApplication()).provinceDao().getAllProvinces()
            retrieved(province)
        }
    }

    private fun fetchProvinceCasesFromEndpoint() {
        apiService.getProvince().enqueue(object : Callback<ProvinceResult> {
            override fun onResponse(call: Call<ProvinceResult>, response: Response<ProvinceResult>) {
                if (response.isSuccessful) {
                    val result = response.body()?.provinceCasesList
                    if (result != null && result.isNotEmpty()) {
                        storeProvinceCasesLocally(result)
                    }
                }
            }

            override fun onFailure(call: Call<ProvinceResult>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun fetchProvinceLocationFromEndPoint() {
        apiService.getProvinceLocation().enqueue(object : Callback<ProvinceLocationResult> {
            override fun onResponse(call: Call<ProvinceLocationResult>, response: Response<ProvinceLocationResult>) {
                if (response.isSuccessful) {
                    val result = response.body()?.locationList
                    if (result != null && result.isNotEmpty()) {
                        locations.value = result
                    }
                }
            }
            override fun onFailure(call: Call<ProvinceLocationResult>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun storeProvinceCasesLocally(list: List<ProvinceCases>?) {
        launch {
            val dao = DatabaseCache(getApplication()).provinceDao()
            dao.deleteAllProvinces()
            dao.insertProvinces(*list!!.toTypedArray())
            retrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    private fun retrieved(list: List<ProvinceCases>) {
        provinceCases.value = list
        error.value = false
    }
}