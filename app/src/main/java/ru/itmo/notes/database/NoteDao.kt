package ru.itmo.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.itmo.notes.entities.Note

@Dao
interface NoteDao {

    @Query("select * from notes_table where is_deleted=0 and folder_id=:folderId")
    fun getNotes(folderId: Int): Flow<List<Note>>

    @Query("select notes_table.* from notes_table inner join folders_table on folder_id = folders_table.id where notes_table.is_deleted=1 and folders_table.is_deleted=0")
    fun getDeletedNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("select * from notes_table where id=:noteId")
    fun getNoteByID(noteId: Int): LiveData<Note>

    @Query("update notes_table set is_deleted=0 where folder_id=:folderId and is_deleted=1")
    suspend fun restoreNotesByFolder(folderId: Int)
}