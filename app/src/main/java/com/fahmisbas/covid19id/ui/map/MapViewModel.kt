package com.fahmisbas.covid19id.ui.map

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.fahmisbas.covid19id.data.*
import com.fahmisbas.covid19id.data.db.DatabaseCache
import com.fahmisbas.covid19id.data.httprequest.ApiService
import com.fahmisbas.covid19id.ui.base.BaseViewModel
import com.fahmisbas.covid19id.util.SharedPreferenceHelper
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MapViewModel(application: Application) : BaseViewModel(application) {


    val provinceData = MutableLiveData<List<ProvinceData>>()
    val error = MutableLiveData<Boolean>()

    private var caseList = arrayListOf<ProvinceCases>()
    private var locationList = arrayListOf<ProvinceLocation>()

    private val apiService = ApiService()

    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun fetch() {
        checkCacheDuration()
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        } else {
            fetchProvinceCasesFromEndpoint()
            fetchProvinceLocationFromEndpoint()
        }
    }

    private fun fetchFromDatabase() {
        launch {
            val provinceData =
                DatabaseCache(getApplication()).provinceDataDao().getAllProvinceData()
            retrieved(provinceData)
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

    private fun fetchProvinceCasesFromEndpoint() {
        apiService.getProvince().enqueue(object : Callback<ProvinceResult> {
            override fun onResponse(
                call: Call<ProvinceResult>,
                response: Response<ProvinceResult>
            ) {
                if (response.isSuccessful) {
                    response.body()?.provinceCasesList.let { result ->
                        if (result != null && result.isNotEmpty()) {
                            caseList = result as ArrayList<ProvinceCases>
                            addProvinceData()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ProvinceResult>, t: Throwable) {
                error.value = true
            }
        })
    }

    private fun fetchProvinceLocationFromEndpoint() {
        apiService.getProvinceLocation().enqueue(object : Callback<ProvinceLocationResult> {
            override fun onResponse(call: Call<ProvinceLocationResult>, response: Response<ProvinceLocationResult>) {
                if (response.isSuccessful) {
                    response.body()?.locationList.let { result ->
                        if (result != null && result.isNotEmpty()) {
                            locationList = result as ArrayList<ProvinceLocation>
                            addProvinceData()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<ProvinceLocationResult>, t: Throwable) {
                error.value = true
            }
        })
    }

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
        Collections.sort(list, reverseOrder())
        provinceData.value = list
        error.value = false
    }
}