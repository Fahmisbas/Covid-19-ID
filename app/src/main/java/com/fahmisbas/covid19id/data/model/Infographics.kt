package com.fahmisbas.covid19id.data.model

import com.google.gson.annotations.SerializedName


data class InfographicsData(

    @SerializedName("infographics")
    val infographicList: List<Infographics>
)

data class Infographics(
    val title: String,
    @SerializedName("url1")
    val picOne: String,
    @SerializedName("url2")
    val picTwo: String,
    @SerializedName("url3")
    val picThree: String,
    @SerializedName("url4")
    val picFour: String
)