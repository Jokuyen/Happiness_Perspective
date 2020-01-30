package com.example.happinessperspective.progress

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happinessperspective.database.EntryDao
import java.lang.IllegalArgumentException

class ProgressViewModelFactory(
    private val dataSource: EntryDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressViewModel::class.java)) {
            return ProgressViewModel(
                dataSource,
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}