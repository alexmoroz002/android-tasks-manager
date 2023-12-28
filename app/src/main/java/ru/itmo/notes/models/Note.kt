package ru.itmo.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes_table",
    foreignKeys = [ForeignKey(
        entity = Folder::class,
        parentColumns = ["id"],
        childColumns = ["folder_id"],
        onDelete = CASCADE
    )]
)
data class Note(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "folder_id") var folderId: Int,
    @ColumnInfo(name = "is_deleted") var isDeleted: Boolean = false,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}