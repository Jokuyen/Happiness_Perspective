package com.jokuyen.happinessperspective.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.jokuyen.happinessperspective.database.EntryDao

class SettingsViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    fun clearDatabase() {
        dao.clear()
    }
}
