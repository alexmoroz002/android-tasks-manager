package ru.itmo.notes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders_table")
data class Folder(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "is_deleted") var isDeleted: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}