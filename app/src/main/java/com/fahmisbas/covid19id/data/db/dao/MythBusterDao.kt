package com.fahmisbas.covid19id.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fahmisbas.covid19id.data.model.MythBuster


@Dao
interface MythBusterDao {

    @Insert
    suspend fun insertMythBuster(vararg mythBuster: MythBuster) : List<Long>

    @Query("SELECT * FROM MythBuster")
    suspend fun getMythBuster() : List<MythBuster>

    @Query("DELETE FROM MythBuster")
    suspend fun deleteMythBuster()
}