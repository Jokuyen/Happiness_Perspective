package com.example.happinessperspective.newEntry

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.happinessperspective.database.EntryDao

class NewEntryViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {

}
