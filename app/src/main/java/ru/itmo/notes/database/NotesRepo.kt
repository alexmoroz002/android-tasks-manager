package ru.itmo.notes.database

import ru.itmo.notes.models.Folder
import ru.itmo.notes.models.Note

class NotesRepo(private val folderDao: FolderDao, private val noteDao: NoteDao) {
    fun getFolders() = folderDao.getFolders()
    fun getFolderByID(folderId: Int) = folderDao.getFolderByID(folderId)
    fun getNotes(folderId: Int) = noteDao.getNotes(folderId)
    fun getNoteById(noteId: Int) = noteDao.getNoteByID((noteId))

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

//    @WorkerThread
    suspend fun insertFolder(folder: Folder) {
        folderDao.insertFolder(folder)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun updateFolder(folder: Folder) {
        folderDao.updateFolder(folder)
    }
}