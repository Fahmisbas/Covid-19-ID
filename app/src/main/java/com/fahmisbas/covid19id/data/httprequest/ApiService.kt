package com.fahmisbas.covid19id.data.httprequest

import com.fahmisbas.covid19id.data.model.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService  {

    private val baseUrlProvinceCases = "https://indonesia-covid-19.mathdro.id/"
    private val baseUrlGithubRaw = "https://raw.githubusercontent.com/"

    private val mathdroidService = Retrofit.Builder()
        .baseUrl(baseUrlProvinceCases)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    private val githubService = Retrofit.Builder()
        .baseUrl(baseUrlGithubRaw)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun getProvince(): Call<ProvinceCasesData> {
        return mathdroidService.getProvince()
    }

    fun getIndonesia(): Call<IndonesiaData> {
        return mathdroidService.getIndonesia()
    }

    fun getQandA(): Call<List<QandA>> {
        return githubService.getWhoQandA()
    }

    fun getMythBuster(): Call<List<MythBuster>> {
        return githubService.getMythBuster()
    }

    fun getProvinceLocation(): Call<ProvinceLocationData> {
        return githubService.getProvinceLocation()
    }

    fun getInfographics(): Call<InfographicsData> {
        return githubService.getInfographics()
    }

}