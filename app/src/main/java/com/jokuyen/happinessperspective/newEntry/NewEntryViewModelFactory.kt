package com.jokuyen.happinessperspective.newEntry

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jokuyen.happinessperspective.database.EntryDao
import java.lang.IllegalArgumentException

class NewEntryViewModelFactory(
    private val dataSource: EntryDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewEntryViewModel::class.java)) {
            return NewEntryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}