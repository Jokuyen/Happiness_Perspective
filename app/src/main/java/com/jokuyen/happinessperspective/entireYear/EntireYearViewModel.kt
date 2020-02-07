package com.jokuyen.happinessperspective.entireYear

import android.app.Application
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

    private var filter = FilterHolder()

    // Setup coroutines
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        initializeList()
    }

    // TEST
    private fun initializeList() {
        uiScope.launch {
            _entries.value = initializeListSuspend()
        }
    }

    private suspend fun initializeListSuspend(): List<Entry> {
        return withContext(Dispatchers.IO) {
            dao.getEntriesForSelectedYearList(currentYear)
        }
    }

    // Clear methods
    fun onClearCurrentYearButtonClick() {
        uiScope.launch {
            clearCurrentYear()
        }
    }

    private suspend fun clearCurrentYear() {
        withContext(Dispatchers.IO) {
            dao.clearCurrentYear(currentYear)
        }

        Toast.makeText(getApplication(), "Deleted All Entries for Current Year!", Toast.LENGTH_SHORT).show()
    }

    // Setup ChipGroup
    private fun onQueryChanged() {
        uiScope.launch {
            _entries.value = getNewList()
        }
    }

    private suspend fun getNewList(): List<Entry> {
        return withContext(Dispatchers.IO) {
            dao.getEntriesForSelectedMonthAndYearList(filter.currentMonthFilter, currentYear)
        }
    }

    fun onFilterChanged(monthFilter: Int, isChecked: Boolean) {
        if (this.filter.update(monthFilter, isChecked)) {
            onQueryChanged()
        }
    }

    private class FilterHolder {
        var currentMonthFilter: Int = -1
            private set

        fun update(newMonthFilter: Int, isChecked: Boolean): Boolean {
            if (isChecked) {
                currentMonthFilter = newMonthFilter
                return true
            }
            else if (currentMonthFilter == newMonthFilter) {
                currentMonthFilter = -1
                return true
            }

            return false
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
