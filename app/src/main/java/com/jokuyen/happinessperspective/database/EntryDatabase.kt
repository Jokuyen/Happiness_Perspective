package com.jokuyen.happinessperspective.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Entry::class], version = 1, exportSchema = false)
abstract class EntryDatabase : RoomDatabase() {

    abstract val entryDao: EntryDao

    companion object {

        @Volatile
        private var INSTANCE: EntryDatabase? = null

        fun getInstance(context: Context): EntryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EntryDatabase::class.java,
                        "entry_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}