package com.example.happinessperspective.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CurrentMonthDetailsViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private val _entries = MutableLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>>
        get() = _entries

    // Coroutine setup
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getEntriesFromDatabase()
    }

    private fun getEntriesFromDatabase() {
        coroutineScope.launch {
            // Fetch data from database
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
