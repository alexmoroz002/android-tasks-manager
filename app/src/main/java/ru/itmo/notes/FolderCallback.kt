package ru.itmo.notes

import ru.itmo.notes.models.Folder

interface FolderCallback {
    fun onClick(folder: Folder)
}