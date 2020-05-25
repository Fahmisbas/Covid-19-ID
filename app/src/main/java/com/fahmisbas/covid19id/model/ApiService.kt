package com.fahmisbas.covid19id.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService  {

    private val coronaCasesBaseUrl = "https://indonesia-covid-19.mathdro.id/"

    private val qandaBaseUrl = "https://raw.githubusercontent.com/"

    private val apiService = Retrofit.Builder()
        .baseUrl(coronaCasesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    private val apiService2 = Retrofit.Builder()
        .baseUrl(qandaBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun getProvince() : Call<ProvinceResult> {
        return apiService.getProvince()
    }

    fun getIndonesia() : Call<Indonesia> {
        return apiService.getIndonesia()
    }

    fun getQandA(): Call<List<QandA>> {
        return apiService2.getWhoQandA()
    }

}