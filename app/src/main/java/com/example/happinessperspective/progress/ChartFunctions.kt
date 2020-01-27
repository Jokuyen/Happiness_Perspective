package com.example.happinessperspective.progress

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class chartXAxisFormatter() : ValueFormatter() {

    private val months = arrayOf("January", "February", "March",
        "April", "May", "June",
        "July", "August", "September",
        "October", "November", "December")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return months.getOrNull(value.toInt()) ?: value.toString()
    }
}