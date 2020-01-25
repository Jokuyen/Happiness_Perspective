package com.example.happinessperspective.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class Entry (
    @PrimaryKey(autoGenerate = true)
    var entryId: Int = 0,

    var date: String,

    var subject: String,

    var note: String?,

    var favorite: Boolean = false
)