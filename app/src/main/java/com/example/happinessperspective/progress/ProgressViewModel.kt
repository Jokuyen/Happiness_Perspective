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
        val monthCount = mutableMapOf<Int, Int>().withDefault { 0 }

        _entries.value?.let {
            for (entry in it) {
                var count = monthCount.getValue(entry.month)
                count++
                monthCount[entry.month] = count
            }
        }

        for ((k, v) in monthCount) {
            chartEntryList.add(BarEntry(k.toFloat(), v.toFloat()))
        }

        val set = BarDataSet(chartEntryList, "Entries")
        set.setValueTextSize(24f)

        return BarData(set)
    }
}
