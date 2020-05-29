package com.fahmisbas.covid19id.data.httprequest

import com.fahmisbas.covid19id.data.Indonesia
import com.fahmisbas.covid19id.data.MythBuster
import com.fahmisbas.covid19id.data.ProvinceResult
import com.fahmisbas.covid19id.data.QandA
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService  {

    private val coronaCasesBaseUrl = "https://indonesia-covid-19.mathdro.id/"

    private val qandaBaseUrl = "https://raw.githubusercontent.com/"

    private val coronaCaseService = Retrofit.Builder()
        .baseUrl(coronaCasesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    private val qandAService = Retrofit.Builder()
        .baseUrl(qandaBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)

    fun getProvince() : Call<ProvinceResult> {
        return coronaCaseService.getProvince()
    }

    fun getIndonesia() : Call<Indonesia> {
        return coronaCaseService.getIndonesia()
    }

    fun getQandA(): Call<List<QandA>> {
        return qandAService.getWhoQandA()
    }

    fun getMythBuster(): Call<List<MythBuster>> {
        return qandAService.getMythBuster()
    }

}