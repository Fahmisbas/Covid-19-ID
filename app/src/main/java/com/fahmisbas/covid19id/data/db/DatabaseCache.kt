package com.fahmisbas.covid19id.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fahmisbas.covid19id.data.db.dao.MythBusterDao
import com.fahmisbas.covid19id.data.db.dao.ProvinceDataDao
import com.fahmisbas.covid19id.data.db.dao.QandADao
import com.fahmisbas.covid19id.data.model.MythBuster
import com.fahmisbas.covid19id.data.model.ProvinceData
import com.fahmisbas.covid19id.data.model.QandA

@Database(entities = arrayOf(QandA::class, MythBuster::class, ProvinceData::class), version = 1)
abstract class DatabaseCache : RoomDatabase(){

    abstract fun qandADao() : QandADao
    abstract fun mythBusterDao() : MythBusterDao
    abstract fun provinceDataDao(): ProvinceDataDao

    companion object{
        @Volatile private var instance : DatabaseCache? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DatabaseCache::class.java
        ,"cachedb"
        ).build()
    }
}