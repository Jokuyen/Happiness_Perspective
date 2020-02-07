package com.jokuyen.happinessperspective.database

import android.icu.text.DateFormatSymbols
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "entry_table")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0,

    var subject: String,

    var note: String?,

    var year: Int,

    // Month range will be 0-11, not 1-12
    var month: Int,

    var day: Int
) : Parcelable {
    // Formatting for Entry ViewHolder (MMM DD, YYYY)
    fun getDateString() : String {
        val monthString = DateFormatSymbols().getMonths()[month]

        return "$monthString $day, $year"
    }
}