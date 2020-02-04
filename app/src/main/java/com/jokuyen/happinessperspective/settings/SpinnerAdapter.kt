package com.jokuyen.happinessperspective.settings

import android.view.View
import android.widget.AdapterView
import com.jokuyen.happinessperspective.CurrentYearSingleton

class SpinnerAdapterListener : AdapterView.OnItemSelectedListener {
    var spinnerCheck = 0 // Avoid calling onItemSelected before user selection

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (++spinnerCheck > 1) {
            val selectedYear = parent.getItemAtPosition(position) as Int
            CurrentYearSingleton.changeCurrentYear(selectedYear)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}