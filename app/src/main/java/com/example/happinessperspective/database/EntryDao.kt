package com.example.happinessperspective.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.Deferred

@Dao
interface EntryDao {
    @Insert
    fun insert(input: Entry)

    @Update
    fun update(input: Entry)

    @Query("SELECT * FROM entry_table WHERE entryId = :key")
    fun get(key: Long): Entry?

    @Query("DELETE FROM entry_table")
    fun clear()

    @Query("SELECT * FROM entry_table ORDER BY entryId DESC")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table " +
            "WHERE strftime('%m', date) = :month AND strftime('%Y', date) = :year " +
            "ORDER BY strftime('%d', date) DESC, entryId DESC")
    fun getEntriesForSelectedMonthAndYear(month: String, year: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table WHERE strftime('%Y', date) = :year ORDER BY strftime('%m', date) DESC, strftime('%d', date) DESC, entryId DESC")
    fun getEntriesForSelectedYear(year: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table ORDER BY entryId DESC LIMIT 1")
    fun getMostRecentEntry(): Entry?
}