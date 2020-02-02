package com.jokuyen.happinessperspective.progress

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao

class ProgressViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private val c = Calendar.getInstance()
    private val currentYear = c.get(Calendar.YEAR).toString()

    private val _entries = dao.getEntriesForSelectedYear(currentYear)
    val entries: LiveData<List<Entry>>
        get() = _entries

    fun getBarChartData() : BarData {
        // Create BarData object
        val chartEntryList = ArrayList<BarEntry>()

        // Count entries for each month
        val monthEntryCount = IntArray(12)

        _entries.value?.let {
            for (entry in it) {
                monthEntryCount[entry.month]++ // Index of 0 is equivalent to January
            }
        }

        for (i in 0..11) {
            chartEntryList.add(BarEntry(i.toFloat(), monthEntryCount[i].toFloat()))
        }

        val set = BarDataSet(chartEntryList, "Entries")

        // Display int instead of float value
        set.setValueFormatter(FloatToIntFormatter())
        set.setValueTextSize(18f)

        return BarData(set)
    }
}
