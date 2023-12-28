package ru.itmo.notes

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.itmo.notes.database.NotesDB
import ru.itmo.notes.database.NotesRepo

class NotesApplication : Application() {
    val appScope = CoroutineScope(SupervisorJob())

    val db by lazy { NotesDB.getDatabase(this, appScope) }
    val repo by lazy { NotesRepo(db.folderDao(), db.noteDao()) }
}