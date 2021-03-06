package com.fahmisbas.covid19id.ui.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.db.DatabaseCache
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.data.model.*
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import com.fahmisbas.covid19id.util.SharedPreferenceHelper
import com.fahmisbas.covid19id.util.sortedLocationsList
import com.fahmisbas.covid19id.util.sortedProvinceCasesList
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MapViewModel(application: Application) : BaseViewModel(application) {

    val provinceData = MutableLiveData<List<ProvinceData>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private var caseList = arrayListOf<ProvinceCases>()
    private var locationList = arrayListOf<ProvinceLocations>()

    private val apiService = ApiService()


    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    override fun fetch() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchFromEndpont()
        }
    }

    fun refresh() {
        fetchFromEndpont()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val provinceDataCache =
                DatabaseCache(getApplication()).provinceDataDao().getAllProvinceData()
            retrieved(provinceDataCache)
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

    private fun fetchFromEndpont() {
        loading.value = true
        apiService.getProvince().enqueue(object : Callback<ProvinceCasesData> {
            override fun onResponse(call: Call<ProvinceCasesData>, response: Response<ProvinceCasesData>) {
                if (response.isSuccessful) {
                    response.body()?.provinceCasesList?.let { result ->
                        if (result.isNotEmpty()) {
                            caseList = sortedProvinceCasesList(result)
                            addProvinceData()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ProvinceCasesData>, t: Throwable) {
                error.value = true
            }
        })
        apiService.getProvinceLocation().enqueue(object : Callback<ProvinceLocationData> {
            override fun onResponse(call: Call<ProvinceLocationData>, response: Response<ProvinceLocationData>) {
                if (response.isSuccessful) {
                    response.body()?.locationsList?.let { result ->
                        if (result.isNotEmpty()) {
                            locationList = sortedLocationsList(result)
                            addProvinceData()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ProvinceLocationData>, t: Throwable) {
                error.value = true
            }
        })
    }

    /*
    after cases and location list is sorted, combining both data
    into ProvinceData ArrayList and store it locally as a cache
     */

    private fun addProvinceData() {
        val provinceData = arrayListOf<ProvinceData>()

        if (caseList.isNotEmpty() && locationList.isNotEmpty()) {
            for (position in 0 until locationList.size) {
                if (caseList.size > 0 && locationList.size > 0) {
                    val location = locationList[position]
                    val cases = caseList[position]

                    provinceData.add(
                        ProvinceData(
                            location.lat, location.lng,
                            cases.provinceName,
                            cases.positive,
                            cases.recovered,
                            cases.death
                        )
                    )
                }
            }
            storeProvinceDataLocally(provinceData)
        }
    }

    private fun storeProvinceDataLocally(provinceData: ArrayList<ProvinceData>) {
        launch {
            val dao = DatabaseCache(getApplication()).provinceDataDao()
            dao.deleteAllProvinceData()
            dao.insertProvinceData(*provinceData.toTypedArray())
            retrieved(provinceData)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    private fun retrieved(list: List<ProvinceData>) {
        provinceData.value = list
        error.value = false
        loading.value = false
    }
}