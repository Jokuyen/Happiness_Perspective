package com.jokuyen.happinessperspective.settings

import android.view.View
import android.widget.AdapterView
import com.jokuyen.happinessperspective.CurrentYearSingleton

class SpinnerAdapterListener : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val selectedYear = parent.getItemAtPosition(position) as Int
        CurrentYearSingleton.changeCurrentYear(selectedYear)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}