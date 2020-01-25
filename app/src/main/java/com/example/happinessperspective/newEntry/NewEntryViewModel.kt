package com.example.happinessperspective.newEntry

import android.app.Application
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao
import kotlinx.coroutines.*

class NewEntryViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private var _date: String = ""
    private var _subject: String = ""
    private var _note: String? = null


    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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
