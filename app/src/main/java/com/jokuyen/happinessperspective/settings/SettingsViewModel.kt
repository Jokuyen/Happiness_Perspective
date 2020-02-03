package com.jokuyen.happinessperspective.settings

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        Toast.makeText(getApplication(), "All Entries Deleted!", Toast.LENGTH_SHORT).show()
    }
}
