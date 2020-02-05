package com.jokuyen.happinessperspective.specificMonth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class SpecificMonthViewModel(
    private val yearArg: String,
    private val monthArg: String,
    private val dao: EntryDao,
    application: Application) : AndroidViewModel(application) {
    private val _entries = dao.getEntriesForSelectedMonthAndYear(monthArg, yearArg)
    val entries : LiveData<List<Entry>>
        get() = _entries

    // Set up coroutines
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun clearCurrentMonthAndYear() {
        withContext(Dispatchers.IO) {
            dao.clearCurrentMonthAndYear(monthArg, yearArg)
        }

        Toast.makeText(getApplication(), "Deleted All Entries for Current Month!", Toast.LENGTH_SHORT).show()
    }

    fun onClearCurrentMonthAndYearButtonClick() {
        uiScope.launch {
            clearCurrentMonthAndYear()
        }
    }
}
