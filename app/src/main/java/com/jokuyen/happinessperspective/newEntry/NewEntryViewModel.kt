package com.jokuyen.happinessperspective.newEntry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class NewEntryViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    // Variables for entry object
    private var _date: String = ""
    private var _subject: String = ""
    private var _note: String? = null
    private var _year: Int = -1
    private var _month: Int = -1
    private var _day: Int = -1

    fun setDate(year: Int, month: Int, day: Int) {
        _year = year
        _month = month
        _day = day

        val monthString = String.format("%02d", month + 1)
        val dayString = String.format("%02d", day)

        _date = year.toString() + "-" + monthString + "-" + dayString
    }

    fun setSubject(input: String) {
        _subject = input
    }

    fun setNote(input: String) {
        _note = input
    }

    // Navigation variables
    private val _navigateToCurrentMonthDetails = MutableLiveData<Boolean>()
    val navigateToCurrentMonthDetails: LiveData<Boolean>
        get() = _navigateToCurrentMonthDetails

    fun onSubmitButtonClicked() {
        _navigateToCurrentMonthDetails.value = true
    }

    fun onNavigatedCompleted() {
        _navigateToCurrentMonthDetails.value = false
    }

    // Database methods
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertEntry() {
        uiScope.launch {
            val newEntry = Entry(date = _date, subject = _subject, note = _note, year = _year, month = _month, day = _day)
            insert(newEntry)
        }
    }

    private suspend fun insert(newEntry: Entry) {
        withContext(Dispatchers.IO) {
            dao.insert(newEntry)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
