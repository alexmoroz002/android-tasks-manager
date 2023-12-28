package ru.itmo.notes

import ru.itmo.notes.models.Note

interface NoteCallback {
    fun onClick(note: Note)
}