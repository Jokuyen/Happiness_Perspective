package com.example.happinessperspective.currentMonth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happinessperspective.database.EntryDao
import java.lang.IllegalArgumentException

class CurrentMonthDetailsViewModelFactory(
    private val dataSource: EntryDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentMonthDetailsViewModel::class.java)) {
            return CurrentMonthDetailsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}