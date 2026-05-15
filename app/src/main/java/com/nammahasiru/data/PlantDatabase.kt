package com.nammahasiru.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// singleton pattern - only one instance of db should exist
// took reference from stackoverflow for this pattern
@Database(entities = [Plant::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

    companion object {

        // @Volatile means changes are immediately visible to other threads
        @Volatile
        private var dbInstance: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            // if instance already exists return it, else create new one
            return dbInstance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "namma_hasiru_database"
                ).build()
                dbInstance = newInstance
                newInstance
            }
        }
    }
}
