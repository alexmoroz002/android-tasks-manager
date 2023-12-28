package ru.itmo.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.notes.database.NotesRepo
import ru.itmo.notes.models.Folder
import ru.itmo.notes.models.Note

class NotesViewModel(private val repo: NotesRepo, private val folderId: Int) : ViewModel() {
    val folder: LiveData<Folder> = repo.getFolderByID(folderId)
    val notes: LiveData<List<Note>> = repo.getNotes(folderId).asLiveData()

    fun insert(note: Note) = viewModelScope.launch {
        repo.insertNote(note)
    }

    fun update(folder: Folder) = viewModelScope.launch {
        repo.updateFolder(folder)
    }
}

class NotesViewModelFactory(private val repo: NotesRepo, private val folderId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repo, folderId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}