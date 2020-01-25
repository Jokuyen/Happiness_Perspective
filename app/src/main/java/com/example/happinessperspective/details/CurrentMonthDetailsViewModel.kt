package com.example.happinessperspective.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao

class CurrentMonthDetailsViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private val _entries = dao.getAllEntries()
    val entries : LiveData<List<Entry>>
        get() = _entries

    override fun onCleared() {
        super.onCleared()
    }
}
