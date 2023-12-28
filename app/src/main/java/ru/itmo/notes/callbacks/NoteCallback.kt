package ru.itmo.notes.callbacks

import ru.itmo.notes.entities.Note

interface NoteCallback {
    fun onClick(note: Note)
}