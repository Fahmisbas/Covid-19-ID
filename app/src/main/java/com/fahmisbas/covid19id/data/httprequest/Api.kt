package com.fahmisbas.covid19id.data.httprequest

import com.fahmisbas.covid19id.data.Indonesia
import com.fahmisbas.covid19id.data.MythBuster
import com.fahmisbas.covid19id.data.ProvinceResult
import com.fahmisbas.covid19id.data.QandA
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("api/provinsi")
    fun getProvince() : Call<ProvinceResult>

    @GET("api")
    fun getIndonesia() : Call<Indonesia>

    @GET("Fahmisbas/MediumNote/9dbedde9a22facb4995077503ae53ac2c1847c97/app/src/main/res/values/who_corona_advice.json")
    fun getWhoQandA() : Call<List<QandA>>

    @GET("Fahmisbas/MediumNote/master/app/src/main/res/values/mythbuster.json")
    fun getMythBuster(): Call<List<MythBuster>>

}