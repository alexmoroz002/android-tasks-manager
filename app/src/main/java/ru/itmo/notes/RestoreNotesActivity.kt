package ru.itmo.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.itmo.notes.adapters.NoteListAdapter
import ru.itmo.notes.callbacks.NoteCallback
import ru.itmo.notes.entities.Note
import ru.itmo.notes.viewmodels.RestoreNotesVM
import ru.itmo.notes.viewmodels.RestoreNotesVMFactory

class RestoreNotesActivity : AppCompatActivity() {
    private val notesViewModel: RestoreNotesVM by viewModels {
        RestoreNotesVMFactory((application as NotesApplication).repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_notes)

        val recyclerView = findViewById<RecyclerView>(R.id.notes_list)
        val adapter = NoteListAdapter(object : NoteCallback {
            override fun onClick(note: Note) {
                notesViewModel.restore(note)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        notesViewModel.notes.observe(this) { folders ->
            folders?.let { adapter.submitList(it) }
        }
    }


}