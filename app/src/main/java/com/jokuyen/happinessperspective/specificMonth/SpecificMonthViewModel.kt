package com.jokuyen.happinessperspective.specificMonth

import android.app.Application
import android.icu.text.DateFormatSymbols
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class SpecificMonthViewModel(
    private val yearArg: Int,
    private val monthArg: Int,
    private val dao: EntryDao,
    application: Application) : AndroidViewModel(application) {

    private val yearArgString = yearArg.toString()
    private val monthArgString = String.format("%02d", monthArg)

    private val _entries = dao.getEntriesForSelectedMonthAndYear(monthArgString, yearArgString)
    val entries : LiveData<List<Entry>>
        get() = _entries

    // Set up coroutines
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun clearCurrentMonthAndYear() {
        withContext(Dispatchers.IO) {
            dao.clearCurrentMonthAndYear(monthArgString, yearArgString)
        }

        Toast.makeText(getApplication(), "Deleted All Entries for Current Month!", Toast.LENGTH_SHORT).show()
    }

    fun onClearCurrentMonthAndYearButtonClick() {
        uiScope.launch {
            clearCurrentMonthAndYear()
        }
    }

    fun getDateString(): String {
        val monthString = DateFormatSymbols().getMonths()[monthArg - 1]

        return monthString + " " + yearArgString
    }
}
