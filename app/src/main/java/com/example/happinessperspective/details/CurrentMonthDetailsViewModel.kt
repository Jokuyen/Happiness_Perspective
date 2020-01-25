package com.example.happinessperspective.details

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao

class CurrentMonthDetailsViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    val c = Calendar.getInstance()
    val currentYear = c.get(Calendar.YEAR).toString()
    val currentMonth = String.format("%02d", c.get(Calendar.MONTH) + 1)

    private val _entries = dao.getEntriesForSelectedMonthAndYear(currentMonth, currentYear)
    val entries : LiveData<List<Entry>>
        get() = _entries

    override fun onCleared() {
        super.onCleared()
    }
}
