package com.fahmisbas.covid19id.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fahmisbas.covid19id.data.model.Infographics

@Dao
interface InfographicsDao {

    @Insert
    suspend fun instetInfographics(vararg infographics: Infographics): List<Long>

    @Query("SELECT * FROM Infographics")
    suspend fun getAllnfographics(): List<Infographics>

    @Query("SELECT * FROM Infographics WHERE uuid = :itemId")
    suspend fun getInfographics(itemId: Int): Infographics

    @Query("DELETE FROM Infographics")
    suspend fun deleteInfographcs()

}