package com.tierriapps.tradestrategytester.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ChartEntity::class], version = 1)
@TypeConverters(ListOfCandlesTypeConverter::class)
abstract class MyRoomDataBase : RoomDatabase() {

    abstract fun myDao(): ChartDAO

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDataBase? = null

        fun getDatabase(context: Context): MyRoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDataBase::class.java,
                    "my_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}