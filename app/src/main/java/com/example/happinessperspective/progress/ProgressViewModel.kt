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

    //private var _entries = MutableLiveData<List<Entry?>>()
    val _entries = dao.getEntriesForSelectedYear(currentYear)
    val entries: LiveData<List<Entry>>
        get() = _entries

    fun getBarChartData() : BarData {
        // Create BarData object
        var chartEntryList = ArrayList<BarEntry>()

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
        set.setValueTextSize(24f)

        // Display int instead of float value
        set.setValueFormatter(FloatToIntFormatter())

        return BarData(set)
    }
}
