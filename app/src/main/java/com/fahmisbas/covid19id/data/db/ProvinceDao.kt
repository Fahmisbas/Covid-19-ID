package com.fahmisbas.covid19id.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fahmisbas.covid19id.data.ProvinceCases


@Dao
interface ProvinceDao {

    @Insert
     suspend fun insertProvinces(vararg provinceCasesCases: ProvinceCases): List<Long>

    @Query("SELECT * FROM ProvinceCases")
     suspend fun getAllProvinces() : List<ProvinceCases>

    @Query("DELETE FROM ProvinceCases")
     suspend fun deleteAllProvinces()

}