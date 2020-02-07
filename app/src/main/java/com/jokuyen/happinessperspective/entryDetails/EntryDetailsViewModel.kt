package com.jokuyen.happinessperspective.entryDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class EntryDetailsViewModel(entry: Entry, private val dao: EntryDao, app: Application) : AndroidViewModel(app) {
    private val _selectedEntry = MutableLiveData<Entry>()
    val selectedEntry: LiveData<Entry>
        get() = _selectedEntry

    init {
        _selectedEntry.value = entry
    }

    // Set up coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private suspend fun clearCurrentEntry() {
        withContext(Dispatchers.IO) {
            dao.clearCurrentEntry(_selectedEntry.value!!.entryId)
        }
    }

    fun onDeleteCurrentEntryButtonClick() {
        uiScope.launch {
            clearCurrentEntry()
        }
    }
}
