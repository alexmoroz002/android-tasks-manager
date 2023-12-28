package ru.itmo.notes.database

import ru.itmo.notes.entities.Folder
import ru.itmo.notes.entities.Note

class NotesRepo(private val folderDao: FolderDao, private val noteDao: NoteDao) {
    fun getFolders() = folderDao.getFolders()
    fun getDeletedFolders() = folderDao.getDeletedFolders()
    fun getFolderByID(folderId: Int) = folderDao.getFolderByID(folderId)
    fun getNotes(folderId: Int) = noteDao.getNotes(folderId)
    fun getDeletedNotes() = noteDao.getDeletedNotes()
    fun getNoteById(noteId: Int) = noteDao.getNoteByID((noteId))

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun insertFolder(folder: Folder) {
        folderDao.insertFolder(folder)
    }

    suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }

    suspend fun updateFolder(folder: Folder) {
        folderDao.updateFolder(folder)
    }

    suspend fun restoreNotes(folder: Folder) {
        noteDao.restoreNotesByFolder(folder.id)
    }
}