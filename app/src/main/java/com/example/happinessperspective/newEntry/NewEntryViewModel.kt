package com.example.happinessperspective.newEntry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.happinessperspective.database.EntryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class NewEntryViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
