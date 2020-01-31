package com.jokuyen.happinessperspective.entryDetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jokuyen.happinessperspective.database.Entry
import java.lang.IllegalArgumentException

class EntryDetailsViewModelFactory(private val entry: Entry, private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryDetailsViewModel::class.java)) {
            return EntryDetailsViewModel(entry, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}