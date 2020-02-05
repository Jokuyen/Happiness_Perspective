package com.jokuyen.happinessperspective.specificMonth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jokuyen.happinessperspective.database.EntryDao

class SpecificMonthViewModelFactory(
    private val yearArg: String,
    private val monthArg: String,
    private val dataSource: EntryDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpecificMonthViewModel::class.java)) {
            return SpecificMonthViewModel(yearArg, monthArg, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}