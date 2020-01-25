package com.example.happinessperspective.newEntry

import android.app.Application
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class NewEntryViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    // Variables for entry object
    private var _date: String = ""
    private var _subject: String = ""
    private var _note: String? = null

    fun setDate(year: Int, month: Int, day: Int) {
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
            val newEntry = Entry(date = _date, subject = _subject, note = _note)
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
