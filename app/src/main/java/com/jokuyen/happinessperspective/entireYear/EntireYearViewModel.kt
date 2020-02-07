package com.jokuyen.happinessperspective.entireYear

import android.app.Application
import android.icu.text.DateFormatSymbols
import android.widget.Toast
import androidx.lifecycle.*
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class EntireYearViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private var currentYear = CurrentYearSingleton.currentYear

    private var filter = FilterHolder()

    private val _entriesForEntireYear = dao.getEntriesForSelectedYear(currentYear)
    val entries = Transformations.map(filter.monthFilter) {
        if (it == -1) {
            _entriesForEntireYear
        }
        else {
            dao.getEntriesForSelectedMonthAndYear(it, currentYear)
        }
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Setup ChipGroup
    fun onFilterChanged(monthFilter: Int, isChecked: Boolean) {
        filter.update(monthFilter, isChecked)
    }

    private class FilterHolder {
        var monthFilter = MutableLiveData(-1)

        fun update(newMonthFilter: Int, isChecked: Boolean) {
            if (isChecked) {
                monthFilter.value = newMonthFilter
            }
            else if (monthFilter.value == newMonthFilter) {
                monthFilter.value = -1
            }
        }
    }

    fun getMonthFilter(): Int? {
        return filter.monthFilter.value
    }

    // Clear selected month
    fun onClearSelectedYearButtonClick() {
        if (filter.monthFilter.value != -1) {
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
            dao.clearSelectedMonthAndYear(filter.monthFilter.value!!, currentYear)
        }

        val monthString = DateFormatSymbols().getMonths()[filter.monthFilter.value!!]
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

        Toast.makeText(getApplication(), "Deleted All Entries for Current Year!", Toast.LENGTH_SHORT).show()
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}
