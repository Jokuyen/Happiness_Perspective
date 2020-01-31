package com.jokuyen.happinessperspective

import android.icu.util.Calendar
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import com.jokuyen.happinessperspective.database.EntryDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntryDatabaseTest: HelperFunction {

    private lateinit var dao: EntryDao
    private lateinit var db: EntryDatabase

    // Variables required to initialize an entry
    val c = Calendar.getInstance()
    val currentYear = c.get(Calendar.YEAR)
    val currentMonth = c.get(Calendar.MONTH)
    val currentDay = c.get(Calendar.DAY_OF_MONTH)

    val monthString = String.format("%02d", currentMonth + 1)
    val dayString = String.format("%02d", currentDay)
    val dateString = currentYear.toString() + "-" + monthString + "-" + dayString

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, EntryDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.entryDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetEntry_ShouldReturnMostRecentEntry() {
        val entry = Entry(date = dateString, subject = "Test Subject", note = null, year = currentYear, month = currentMonth, day = currentDay)
        dao.insert(entry)
        val result = dao.getMostRecentEntry()

        // Automatically-generated entryId starts at 1
        assertEquals(entry.entryId + 1, result?.entryId)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteEntry_ShouldReturnNull() {
        val entry = Entry(date = dateString, subject = "Test Subject", note = null, year = currentYear, month = currentMonth, day = currentDay)
        dao.insert(entry)
        dao.clear()

        val result = dao.getMostRecentEntry()

        assertEquals(null, result?.date)
    }

    @Test
    @Throws(Exception::class)
    fun getEntriesForSelectedYear_ShouldReturnEntriesStartingWithMostRecent() {
        val entry1 = Entry(date = dateString, subject = "Test Subject 1", note = null, year = currentYear, month = currentMonth, day = currentDay)
        val entry2 = Entry(date = dateString, subject = "Test Subject 2", note = null, year = currentYear, month = currentMonth, day = currentDay)
        val entry3 = Entry(date = dateString, subject = "Test Subject 3", note = null, year = currentYear, month = currentMonth, day = currentDay)
        val entry4 = Entry(date = dateString, subject = "Test Subject 4", note = null, year = currentYear, month = currentMonth, day = currentDay)
        val entry5 = Entry(date = dateString, subject = "Test Subject 5", note = null, year = currentYear, month = currentMonth, day = currentDay)

        dao.insert(entry1)
        dao.insert(entry2)
        dao.insert(entry3)
        dao.insert(entry4)
        dao.insert(entry5)

        val results = dao.getEntriesForSelectedYear(currentYear.toString())

        // First value of the list should be most recent entry, which would be entry5
        assertEquals(5, results.getOrAwaitValue()[0].entryId)
        assertEquals(4, results.getOrAwaitValue()[1].entryId)
        assertEquals(3, results.getOrAwaitValue()[2].entryId)
        assertEquals(2, results.getOrAwaitValue()[3].entryId)
        assertEquals(1, results.getOrAwaitValue()[4].entryId)
    }
}