package com.fahmisbas.covid19id.data.model

import com.google.gson.annotations.SerializedName


data class IndonesiaData(
    @SerializedName("perawatan")
    val positive: String,
    @SerializedName("sembuh")
    val recovered: String,
    @SerializedName("meninggal")
    val death: String,
    @SerializedName("jumlahKasus")
    val caseNumber: String
)