package com.jokuyen.happinessperspective.database

import android.icu.text.DateFormatSymbols
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "entry_table")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0,

    // Month range will be 01-12, not 00-11
    var date: String,

    var subject: String,

    var note: String?,

    var year: Int,

    // Month range will be 0-11, not 1-12
    var month: Int,

    var day: Int
) : Parcelable {
    // Formatting for Entry ViewHolder (MMM DD, YYYY)
    fun getDateString() : String {
        val dateObject = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
        val monthString = DateFormatSymbols().getMonths()[dateObject.monthValue - 1]

        return monthString + " " + dateObject.dayOfMonth + ", " + dateObject.year
    }
}