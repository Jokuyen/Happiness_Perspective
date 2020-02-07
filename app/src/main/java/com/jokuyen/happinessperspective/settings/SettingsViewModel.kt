package com.jokuyen.happinessperspective.settings

import android.app.Application
import android.icu.util.Calendar
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class SettingsViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {

    private val _years = dao.getYears()
    val yearsArray: LiveData<Array<Int>>
        get() = _years

    // Coroutine setup
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Clear database
    fun onClearDatabaseButtonClick() {
        uiScope.launch {
            clearDatabase()
        }
    }

    private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            dao.clear()
        }

        // Reset CurrentYearSingleton to default current year
        val c = Calendar.getInstance()
        CurrentYearSingleton.changeCurrentYear(c.get(Calendar.YEAR))

        Toast.makeText(getApplication(), "All Entries Deleted!", Toast.LENGTH_LONG).show()
    }
}
