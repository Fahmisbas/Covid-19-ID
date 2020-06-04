package com.fahmisbas.covid19id.data.httprequest

import com.fahmisbas.covid19id.data.model.*
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("api/provinsi")
    fun getProvince(): Call<ProvinceCasesData>

    @GET("api")
    fun getIndonesia(): Call<IndonesiaData>

    @GET("Fahmisbas/NoteApp/master/app/src/main/res/values/who_corona_advice.json")
    fun getWhoQandA() : Call<List<QandA>>

    @GET("Fahmisbas/NoteApp/master/app/src/main/res/values/mythbuster.json")
    fun getMythBuster(): Call<List<MythBuster>>

    @GET("Fahmisbas/Covid-19-ID/master/indonesia_provinces_location.json")
    fun getProvinceLocation(): Call<ProvinceLocationData>

    @GET("Fahmisbas/COVID19-ID-Tracker/master/who_corona_infographics.json")
    fun getInfographics(): Call<InfographicsData>

}