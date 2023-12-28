package ru.itmo.notes.callbacks

import ru.itmo.notes.entities.Folder

interface FolderCallback {
    fun onClick(folder: Folder)
}