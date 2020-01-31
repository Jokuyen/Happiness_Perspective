package com.jokuyen.happinessperspective.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class SettingsViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            dao.clear()
        }
    }

    fun onClearDatabaseButtonClick() {
        uiScope.launch {
            clearDatabase()
        }
    }

}
