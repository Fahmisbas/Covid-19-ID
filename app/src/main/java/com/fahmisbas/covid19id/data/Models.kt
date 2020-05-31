package com.fahmisbas.covid19id.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ProvinceResult(

    @SerializedName("data")
    val provinceList : List<Province>
)

@Entity
data class Province(

    @SerializedName("provinsi")
    val provinceName : String?,
    @SerializedName("kasusPosi")
    val positive : String?,
    @SerializedName("kasusSemb")
    val recovered : String?,
    @SerializedName("kasusMeni")
    val death : String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}

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


@Entity
data class QandA(
    val title: String,
    val subtitle: String
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}

@Entity
data class MythBuster(val url: String?){@PrimaryKey(autoGenerate = true) var uuid : Int = 0}