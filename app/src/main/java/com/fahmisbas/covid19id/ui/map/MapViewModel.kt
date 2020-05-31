package com.fahmisbas.covid19id.ui.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.Province
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

    private val apiService = ApiService()

    val province = MutableLiveData<List<Province>>()
    val error = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun fetch() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        }else {
            fetchFromEndpoint();
        }
    }

    private fun checkCacheDuration() {
        val cachePreference = prefHelper.getCacheDuration()
        try {
            val cachePreferenceInt = cachePreference?.toInt() ?: 5 * 60
            refreshTime = cachePreferenceInt.times(1000*1000*1000L)
        }catch (e : NumberFormatException){
            e.printStackTrace()
        }
    }

    private fun fetchFromDatabase(){
        launch {
            val province = DatabaseCache(getApplication()).provinceDao().getAllProvinces()
            retrieved(province)
        }
    }

    private fun fetchFromEndpoint() {
        apiService.getProvince().enqueue(object : Callback<ProvinceResult> {
            override fun onResponse(call: Call<ProvinceResult>, response: Response<ProvinceResult>) {
                val body = response.body()?.provinceList
                storeLocally(body)
            }

            override fun onFailure(call: Call<ProvinceResult>, t: Throwable) {
                error.value = true
                errorMessage.value = t.message
            }
        })
    }

    private fun storeLocally(list: List<Province>?) {
        launch {
            val dao = DatabaseCache(getApplication()).provinceDao()
            dao.deleteAllProvinces()
            dao.insertProvinces(*list!!.toTypedArray())
            retrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    private fun retrieved(list: List<Province>) {
        province.value = list
        error.value = false
    }
}