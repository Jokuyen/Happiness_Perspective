package com.example.happinessperspective.entryDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.happinessperspective.database.Entry

class EntryDetailsViewModel(entry: Entry, app: Application) : AndroidViewModel(app) {
    private val _selectedEntry = MutableLiveData<Entry>()
    val selectedEntry: LiveData<Entry>
        get() = _selectedEntry

    init {
        _selectedEntry.value = entry
    }
}
