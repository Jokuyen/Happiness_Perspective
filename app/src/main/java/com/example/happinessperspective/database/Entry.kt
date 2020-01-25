package com.example.happinessperspective.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0,

    var subject: String? = null,

    var note: String? = null,

    var year: Int = -1,

    var month: Int = -1,

    var day: Int = -1,

    var favorite: Boolean = false
)