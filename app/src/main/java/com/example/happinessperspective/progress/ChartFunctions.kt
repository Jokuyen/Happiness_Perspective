package com.example.happinessperspective.progress

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class ChartXAxisFormatter() : ValueFormatter() {

    private val months = arrayOf("Jan", "Feb", "Mar",
        "Apr", "May", "Jun",
        "Jul", "Aug", "Sep",
        "Oct", "Nov", "Dec")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return months.getOrNull(value.toInt()) ?: value.toString()
    }
}