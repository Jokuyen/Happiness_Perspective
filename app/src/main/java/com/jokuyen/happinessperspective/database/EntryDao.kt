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

    // Get list of existing years
    @Query("SELECT DISTINCT year FROM entry_table ORDER BY year ASC")
    fun getYears(): LiveData<Array<Int>>

    // Get entries methods
    @Query("SELECT * FROM entry_table WHERE entryId = :key")
    fun get(key: Long): Entry?

    @Query("SELECT * FROM entry_table ORDER BY entryId DESC LIMIT 1")
    fun getMostRecentEntry(): Entry?

    @Query("SELECT * FROM entry_table ORDER BY entryId ASC")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table WHERE month = :inputMonth AND year = :inputYear ORDER BY day ASC, entryId ASC")
    fun getEntriesForSelectedMonthAndYear(inputMonth: Int, inputYear: Int): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table WHERE year = :inputYear ORDER BY month ASC, day ASC, entryId ASC")
    fun getEntriesForSelectedYear(inputYear: Int): LiveData<List<Entry>>

    // Get entries methods for Non-LiveData list
    @Query("SELECT * FROM entry_table WHERE month = :inputMonth AND year = :inputYear ORDER BY day ASC, entryId ASC")
    fun getEntriesForSelectedMonthAndYearList(inputMonth: Int, inputYear: Int): List<Entry>

    @Query("SELECT * FROM entry_table WHERE year = :inputYear ORDER BY month ASC, day ASC, entryId ASC")
    fun getEntriesForSelectedYearList(inputYear: Int): List<Entry>

    // Clear methods
    @Query("DELETE FROM entry_table")
    fun clear()

    @Query("DELETE FROM entry_table WHERE entryId = :entryId")
    fun clearCurrentEntry(entryId: Int)

    @Query("DELETE FROM entry_table WHERE month = :inputMonth AND year = :inputYear")
    fun clearCurrentMonthAndYear(inputMonth: Int, inputYear: Int)

    @Query("DELETE FROM entry_table WHERE year = :inputYear")
    fun clearCurrentYear(inputYear: Int)
}