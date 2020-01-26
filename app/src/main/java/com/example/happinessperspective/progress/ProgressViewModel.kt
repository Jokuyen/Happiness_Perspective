package com.example.happinessperspective.progress

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.happinessperspective.database.Entry
import com.example.happinessperspective.database.EntryDao
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class ProgressViewModel(val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    val c = Calendar.getInstance()
    val currentYear = c.get(Calendar.YEAR).toString()

    private val _entries = dao.getEntriesForSelectedYear(currentYear)
    val entries : LiveData<List<Entry>>
        get() = _entries

    fun getBarChartData() : BarData {
        // Get count of entries from list result of SelectedMonthAndYear
        var listSize: Int = entries.value?.size ?: 0

        // Create BarData object
        var chartEntryList = ArrayList<BarEntry>()

        //chartEntryList.add(BarEntry(0f, listSize.toFloat()))
        chartEntryList.add(BarEntry(0f, 10f))

        val set = BarDataSet(chartEntryList, "Entries")
        set.setValueTextSize(24f)

        return BarData(set)
    }
}
