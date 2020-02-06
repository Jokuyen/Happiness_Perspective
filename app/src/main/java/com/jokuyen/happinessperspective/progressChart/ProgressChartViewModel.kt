package com.jokuyen.happinessperspective.progressChart

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.jokuyen.happinessperspective.CurrentYearSingleton
import com.jokuyen.happinessperspective.R
import com.jokuyen.happinessperspective.database.Entry
import com.jokuyen.happinessperspective.database.EntryDao

class ProgressChartViewModel(private val dao: EntryDao, application: Application) : AndroidViewModel(application) {
    private val currentYear = CurrentYearSingleton.currentYear.toString()

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

        val myColor = ContextCompat.getColor(getApplication(), R.color.secondaryColor)

        set.setColor(myColor)


        // Display int instead of float value
        set.setValueFormatter(FloatToIntFormatter())
        set.setValueTextSize(18f)

        return BarData(set)
    }
}
