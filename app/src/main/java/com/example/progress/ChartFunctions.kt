package com.example.progress

import android.icu.text.DateFormatSymbols
import android.icu.util.Calendar
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class chartXAxisFormatter() : ValueFormatter() {
    val c = Calendar.getInstance()
    val currentYear = c.get(Calendar.YEAR).toString()

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {

        return DateFormatSymbols().getMonths()[c.get(Calendar.MONTH)]
    }
}