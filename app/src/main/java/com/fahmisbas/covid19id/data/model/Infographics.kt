package com.fahmisbas.covid19id.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class InfographicsData(

    @SerializedName("infographics")
    val infographicList: List<Infographics>
)

@Entity
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
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}