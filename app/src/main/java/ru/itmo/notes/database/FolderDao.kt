package ru.itmo.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.itmo.notes.models.Folder

@Dao
interface FolderDao {
//    @Query("select * from folders_table order by title asc")
//    fun getAllFolders(): Flow<List<Folder>>

    @Query("select * from folders_table where is_deleted=0 order by title asc")
    fun getFolders(): Flow<List<Folder>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFolder(folder: Folder)

    @Update
    suspend fun updateFolder(folder: Folder)

    @Query("select * from folders_table where id=:folderId")
    fun getFolderByID(folderId: Int): LiveData<Folder>

//    @Query("delete from folders_table")
//    suspend fun deleteAllFolders()
}