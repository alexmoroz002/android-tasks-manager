package ru.itmo.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itmo.notes.database.NotesRepo
import ru.itmo.notes.models.Folder

class FoldersViewModel(private val repo: NotesRepo) : ViewModel() {
    val folders: LiveData<List<Folder>> = repo.getFolders().asLiveData()

    fun insert(folder: Folder) = viewModelScope.launch {
        repo.insertFolder(folder)
    }
}

class FoldersViewModelFactory(private val repo: NotesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoldersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoldersViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}