package ru.itmo.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.itmo.notes.models.Note

@Dao
interface NoteDao {
//    @Query("select * from notes_table order by title asc")
//    fun getAllNotes(): Flow<List<Note>>

    @Query("select * from notes_table where is_deleted=0 and folder_id=:folderId order by title asc")
    fun getNotes(folderId: Int): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("select * from notes_table where id=:noteId")
    fun getNoteByID(noteId: Int): LiveData<Note>

//    @Query("delete from notes_table")
//    suspend fun deleteAllNotes()
}