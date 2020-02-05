package com.jokuyen.happinessperspective.allMonths

import android.app.Application
import android.icu.util.Calendar
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class AllMonthsViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private val c = Calendar.getInstance()
    private var currentYear = CurrentYearSingleton.currentYear.toString()

    private val _entries = dao.getEntriesForSelectedYear(currentYear)
    val entries : LiveData<List<Entry>>
        get() = _entries

    // Set up coroutines
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun clearCurrentYear() {
        withContext(Dispatchers.IO) {
            dao.clearCurrentYear(currentYear)
        }

        Toast.makeText(getApplication(), "Deleted All Entries for Current Year!", Toast.LENGTH_SHORT).show()
    }

    fun onClearCurrentYearButtonClick() {
        uiScope.launch {
            clearCurrentYear()
        }
    }
}
