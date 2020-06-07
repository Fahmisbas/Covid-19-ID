package com.fahmisbas.covid19id.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fahmisbas.covid19id.data.model.QandA

@Dao
interface QandADao{

    @Insert
    suspend fun insertQandA(vararg qandAModel: QandA) : List<Long>

    @Query("SELECT * FROM QandA")
    suspend fun getQandA() : List<QandA>

    @Query("DELETE FROM QandA")
    suspend fun deleteQandA()

}