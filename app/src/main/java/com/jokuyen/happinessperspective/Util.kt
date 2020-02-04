package com.jokuyen.happinessperspective

object CurrentYearSingleton {
    private var _currentYear = 0;
    val currentYear: Int
        get() = _currentYear

    fun changeCurrentYear(input: Int) {
        _currentYear = input
    }
}