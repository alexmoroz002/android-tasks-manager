package ru.itmo.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.itmo.notes.entities.Folder
import ru.itmo.notes.entities.Note

@Database(entities = [Folder::class, Note::class], version = 1, exportSchema = false)
abstract class NotesDB : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NotesDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NotesDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDB::class.java,
                    "app"
                ).addCallback(AppDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val folderDao = database.folderDao()
                    val noteDao = database.noteDao()
                    // Add sample words.
                    var item = Folder("Hello11")
                    folderDao.insertFolder(item)
                    item = Folder("World!11")
                    folderDao.insertFolder(item)
                    noteDao.insertNote(Note("12", "1111", 1))
                    noteDao.insertNote(Note("1as122", "1111", 1))
                    noteDao.insertNote(Note("xdd", "1111", 2))
                    noteDao.insertNote(Note("qqqqqqqqq1adad2", "qq", 1, true))

                    item = Folder("TODO!11", true)
                    folderDao.insertFolder(item)
                }
            }
        }
    }
}