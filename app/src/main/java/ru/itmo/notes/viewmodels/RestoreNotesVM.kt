package ru.itmo.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.notes.database.NotesRepo
import ru.itmo.notes.entities.Note

class RestoreNotesVM(private val repo: NotesRepo) : ViewModel() {
    val notes: LiveData<List<Note>> = repo.getDeletedNotes().asLiveData()

    fun restore(note: Note) = viewModelScope.launch {
        note.isDeleted = false
        repo.updateNote(note)
    }
}

class RestoreNotesVMFactory(private val repo: NotesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestoreNotesVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestoreNotesVM(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}