package com.jokuyen.happinessperspective.currentMonth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jokuyen.happinessperspective.database.EntryDao

class CurrentMonthDetailsViewModelFactory(
    private val currentYear: String,
    private val currentMonth: String,
    private val dataSource: EntryDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentMonthDetailsViewModel::class.java)) {
            return CurrentMonthDetailsViewModel(currentYear, currentMonth, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}