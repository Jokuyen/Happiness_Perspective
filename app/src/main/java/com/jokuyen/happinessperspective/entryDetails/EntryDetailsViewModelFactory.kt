package com.jokuyen.happinessperspective.entryDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao

class EntryDetailsViewModelFactory(
    private val entry: Entry,
    private val dataSource: EntryDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryDetailsViewModel::class.java)) {
            return EntryDetailsViewModel(entry, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}