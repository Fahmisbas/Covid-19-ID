package com.fahmisbas.covid19id.model

import com.google.gson.annotations.SerializedName


data class ProvinceResult(

    @SerializedName("data")
    val provinceList : List<Province>
)

data class Province(

    @SerializedName("provinsi")
    val provinceName : String?,
    @SerializedName("kasusPosi")
    val positive : String?,
    @SerializedName("kasusSemb")
    val recovered : String?,
    @SerializedName("kasusMeni")
    val death : String?
)

data class Indonesia(
    @SerializedName("perawatan")
    val positive : String,
    @SerializedName("sembuh")
    val recovered : String,
    @SerializedName("meninggal")
    val death : String,
    @SerializedName("jumlahKasus")
    val caseNumber : String
)

data class QandA(
    val title : String,
    val subtitle : String
)

data class MythBuster(val url: String?)