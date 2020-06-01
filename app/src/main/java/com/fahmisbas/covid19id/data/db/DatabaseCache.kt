package com.fahmisbas.covid19id.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fahmisbas.covid19id.data.MythBuster
import com.fahmisbas.covid19id.data.ProvinceCases
import com.fahmisbas.covid19id.data.QandA

@Database(entities = arrayOf(ProvinceCases::class,QandA::class,MythBuster::class),version = 1)
abstract class DatabaseCache : RoomDatabase(){

    abstract fun provinceDao() : ProvinceDao
    abstract fun qandADao() : QandADao
    abstract fun mythBusterDao() : MythBusterDao

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