package com.example.happinessperspective.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _navigateToNewEntry = MutableLiveData<Boolean>()
    val navigateToNewEntry: LiveData<Boolean>
        get() = _navigateToNewEntry

    fun onFabClicked() {
        _navigateToNewEntry.value = true
    }

    fun onNavigatedCompleted() {
        _navigateToNewEntry.value = false
    }
}