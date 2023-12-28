package ru.itmo.notes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.notes.database.NotesRepo
import ru.itmo.notes.entities.Folder

class RestoreFoldersVM(private val repo: NotesRepo) : ViewModel() {
    val folders: LiveData<List<Folder>> = repo.getDeletedFolders().asLiveData()

    fun restore(folder: Folder) = viewModelScope.launch {
        folder.isDeleted = false
        repo.updateFolder(folder)
        repo.restoreNotes(folder)
    }
}

class RestoreFoldersVMFactory(private val repo: NotesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestoreFoldersVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestoreFoldersVM(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}