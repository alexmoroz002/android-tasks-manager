package ru.itmo.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.notes.database.NotesRepo
import ru.itmo.notes.entities.Note

class NoteDetailViewModel(private val repo: NotesRepo, private val noteId: Int) : ViewModel() {
    val note: LiveData<Note> = repo.getNoteById(noteId)

    fun update(note: Note) = viewModelScope.launch {
        repo.updateNote(note)
    }
}

class NoteDetailViewModelFactory(private val repo: NotesRepo, private val noteId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteDetailViewModel(repo, noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}