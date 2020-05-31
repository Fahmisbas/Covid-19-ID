package com.fahmisbas.covid19id.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fahmisbas.covid19id.data.Province


@Dao
interface ProvinceDao {

    @Insert
     suspend fun insertProvinces(vararg provinces: Province): List<Long>

    @Query("SELECT * FROM Province")
     suspend fun getAllProvinces() : List<Province>

    @Query("DELETE FROM Province")
     suspend fun deleteAllProvinces()

}