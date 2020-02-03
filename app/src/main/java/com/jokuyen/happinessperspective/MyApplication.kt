package com.jokuyen.happinessperspective

import android.app.Application
import android.icu.util.Calendar


class MyApplication: Application() {
    // Create a year variable used throughout the app that can be changed by user
    private val c = Calendar.getInstance()

    private var _currentYear: Int = c.get(Calendar.YEAR)

    val currentYear: Int
        get() = _currentYear

    fun setCurrentYear(inputYear: Int) {
        _currentYear = inputYear
    }
}