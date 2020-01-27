package com.example.happinessperspective.database

import android.icu.text.DateFormatSymbols
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "entry_table")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0,

    var date: String,

    var subject: String,

    var note: String?,

    var favorite: Boolean = false,

    var year: Int,

    var month: Int,

    var day: Int
) {
    // Formatting for Entry ViewHolder (MMM DD, YYYY)
    fun getDateString() : String {
        val dateObject = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        val monthString = DateFormatSymbols().getMonths()[dateObject.monthValue - 1]

        return monthString + " " + dateObject.dayOfMonth + ", " + dateObject.year
    }
}