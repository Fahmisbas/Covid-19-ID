package com.fahmisbas.covid19id.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class ProvinceData(
    val lat: String,
    val lng: String,
    val provinceName: String,
    val positive: String,
    val recovered: String,
    val death: String
) : Comparable<ProvinceData> {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
    override fun compareTo(other: ProvinceData): Int {
        return provinceName.compareTo(other.provinceName)
    }
}

data class ProvinceCasesData(

    @SerializedName("data")
    val provinceCasesList: List<ProvinceCases>
)

data class ProvinceCases(

    @SerializedName("provinsi")
    val provinceName: String,
    @SerializedName("kasusPosi")
    val positive: String,
    @SerializedName("kasusSemb")
    val recovered: String,
    @SerializedName("kasusMeni")
    val death: String
) : Comparable<ProvinceCases> {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

    override fun compareTo(other: ProvinceCases): Int {
        var compare = provinceName.compareTo(other.provinceName)
        if (compare == 0) {
            compare = Integer.compare(positive.toInt(), other.positive.toInt())
        }
        return compare
    }
}

data class ProvinceLocationData(

    @SerializedName("lacation")
    val locationList: List<ProvinceLocation>

)

data class ProvinceLocation(
    val lat: String,
    val lng: String,
    @SerializedName("provinsi")
    val provinceName: String
) : Comparable<ProvinceLocation> {
    override fun compareTo(other: ProvinceLocation): Int {
        var compare = provinceName.compareTo(other.provinceName)
        if (compare == 0) {
            compare = Integer.compare(lat.toInt(), other.lat.toInt())
        }
        return compare
    }
}