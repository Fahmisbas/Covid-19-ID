package com.fahmisbas.covid19id.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fahmisbas.covid19id.data.ProvinceData

@Dao
interface ProvinceDataDao {

    @Insert
    suspend fun insertProvinceData(vararg provinceData: ProvinceData): List<Long>

    @Query("SELECT * FROM ProvinceData")
    suspend fun getAllProvinceData(): List<ProvinceData>

    @Query("DELETE FROM ProvinceData")
    suspend fun deleteAllProvinceData()

}