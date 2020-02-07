package com.jokuyen.happinessperspective.entireYear

import android.app.Application
import android.icu.text.DateFormatSymbols
import android.icu.util.Calendar
import android.widget.Toast
import androidx.lifecycle.*
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class EntireYearViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private val c = Calendar.getInstance()
    private var currentYear = CurrentYearSingleton.currentYear

    private var _entries = MutableLiveData<List<Entry>>()
    val entries : LiveData<List<Entry>>
        get() = _entries

    private val _entriesChangedStatus = dao.getAllEntries()
    val entriesChangedStatus : LiveData<List<Entry>>
        get() = _entriesChangedStatus

    private var filter = FilterHolder()

    // Setup coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        getEntireYear()
    }

    private fun getEntireYear() {
        uiScope.launch {
            _entries.value = getEntireYearList()
        }
    }

    private suspend fun getEntireYearList(): List<Entry> {
        return withContext(Dispatchers.IO) {
            dao.getEntriesForSelectedYearList(currentYear)
        }
    }

    // Clear selected month
    fun onClearSelectedYearButtonClick() {
        if (filter.currentMonthFilter != -1) {
            uiScope.launch {
                clearSelectedMonth()
            }
        }
        else {
            Toast.makeText(getApplication(), "No Month Selected!", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun clearSelectedMonth() {
        withContext(Dispatchers.IO) {
            dao.clearSelectedMonthAndYear(filter.currentMonthFilter, currentYear)
        }

        _entries.value = listOf()

        val monthString = DateFormatSymbols().getMonths()[filter.currentMonthFilter]
        Toast.makeText(getApplication(), "Deleted All Entries for $monthString!", Toast.LENGTH_SHORT).show()
    }

    // Clear entire year
    fun onClearCurrentYearButtonClick() {
        uiScope.launch {
            clearCurrentYear()
        }
    }

    private suspend fun clearCurrentYear() {
        withContext(Dispatchers.IO) {
            dao.clearCurrentYear(currentYear)
        }

        _entries.value = listOf()
        Toast.makeText(getApplication(), "Deleted All Entries for Current Year!", Toast.LENGTH_SHORT).show()
    }

    // Setup ChipGroup
    fun onFilterChanged(monthFilter: Int, isChecked: Boolean) {
        filter.update(monthFilter, isChecked)

        if (filter.currentMonthFilter != -1)
        {
            filterChanged()
        }
        else {
            getEntireYear()
        }
    }

    private fun filterChanged() {
        uiScope.launch {
            _entries.value = getFilteredList()
        }
    }

    private suspend fun getFilteredList(): List<Entry> {
        return withContext(Dispatchers.IO) {
            dao.getEntriesForSelectedMonthAndYearList(filter.currentMonthFilter, currentYear)
        }
    }

    private class FilterHolder {
        var currentMonthFilter: Int = -1
            private set

        fun update(newMonthFilter: Int, isChecked: Boolean) {
            if (isChecked) {
                currentMonthFilter = newMonthFilter
            }
            else if (currentMonthFilter == newMonthFilter) {
                currentMonthFilter = -1
            }
        }
    }

    fun refreshList() {
        getEntireYear()
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
