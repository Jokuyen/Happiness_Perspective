package com.example.happinessperspective

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao
import com.example.happinessperspective.database.EntryDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntryDatabaseTest {

    private lateinit var dao: EntryDao
    private lateinit var db: EntryDatabase

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
    fun insertAndGetEntry() {
        val entry = Entry()
        dao.insert(entry)
        val mostRecentEntry = dao.getMostRecentEntry()
        assertEquals(mostRecentEntry?.day, entry.day)
    }
}