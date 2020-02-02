package com.jokuyen.happinessperspective.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EntryDao {
    @Insert
    fun insert(input: Entry)

    @Update
    fun update(input: Entry)

    @Query("SELECT * FROM entry_table WHERE entryId = :key")
    fun get(key: Long): Entry?

    @Query("SELECT * FROM entry_table ORDER BY entryId DESC LIMIT 1")
    fun getMostRecentEntry(): Entry?

    @Query("SELECT * FROM entry_table ORDER BY entryId DESC")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table " +
            "WHERE strftime('%m', date) = :month AND strftime('%Y', date) = :year " +
            "ORDER BY strftime('%d', date) DESC, entryId DESC")
    fun getEntriesForSelectedMonthAndYear(month: String, year: String): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table " +
            "WHERE strftime('%Y', date) = :year " +
            "ORDER BY strftime('%m', date) DESC, strftime('%d', date) DESC, entryId DESC")
    fun getEntriesForSelectedYear(year: String): LiveData<List<Entry>>

    @Query("DELETE FROM entry_table")
    fun clear()

    @Query("DELETE FROM entry_table WHERE entryId = :entryId")
    fun clearCurrentEntry(entryId: Int)

    @Query("DELETE FROM entry_table WHERE strftime('%m', date) = :month AND strftime('%Y', date) = :year")
    fun clearCurrentMonthAndYear(month: String, year: String)
}