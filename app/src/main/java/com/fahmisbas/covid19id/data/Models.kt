package com.fahmisbas.covid19id.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class ProvinceLocationResult(

    @SerializedName("lacation")
    val locationList : List<ProvinceLocation>

)

data class ProvinceLocation(

    val lat : String,
    val lng : String,
    @SerializedName("provinsi")
    val provinceName: String

) : Comparable<ProvinceLocation>
{
    override fun compareTo(other: ProvinceLocation): Int {
        var compare = provinceName.compareTo(other.provinceName)
        if (compare == 0){
            compare = Integer.compare(lat.toInt(), other.lat.toInt())
        }
        return compare
    }
}

data class ProvinceResult(

    @SerializedName("data")
    val provinceCasesList : List<ProvinceCases>
)

@Entity
data class ProvinceCases(

    @SerializedName("provinsi")
    val provinceName : String,
    @SerializedName("kasusPosi")
    val positive : String,
    @SerializedName("kasusSemb")
    val recovered : String,
    @SerializedName("kasusMeni")
    val death : String
) : Comparable<ProvinceCases>
{
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0

    override fun compareTo(other: ProvinceCases): Int {
        var compare = provinceName.compareTo(other.provinceName)
        if (compare == 0){
            compare = Integer.compare(positive.toInt(),other.positive.toInt())
        }
        return compare
    }
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
data class QandA(val title: String, val subtitle: String){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}

@Entity
data class MythBuster(val url: String?){@PrimaryKey(autoGenerate = true) var uuid : Int = 0}