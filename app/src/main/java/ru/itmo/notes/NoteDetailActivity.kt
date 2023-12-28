package ru.itmo.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.itmo.notes.viewmodels.NoteDetailViewModel
import ru.itmo.notes.viewmodels.NoteDetailViewModelFactory

class NoteDetailActivity : AppCompatActivity() {
    private var noteId: Int = 1
    private val noteDetailViewModel: NoteDetailViewModel by viewModels {
        NoteDetailViewModelFactory((application as NotesApplication).repo, noteId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        noteId = intent.getIntExtra("noteId", 1)
        val titleView = findViewById<TextView>(R.id.title)
        val textView = findViewById<TextView>(R.id.text)

        noteDetailViewModel.note.observe(this) {
            titleView.text = it.title
            textView.text = it.text
        }

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val editNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val title = it.data?.getStringExtra(NewNoteActivity.TITLE_REPLY)
                val text = it.data?.getStringExtra(NewNoteActivity.TEXT_REPLY)
                if (title != null && text != null) {
                    val note = noteDetailViewModel.note.value
                    if (note != null) {
                        note.title = title
                        note.text = text
                        noteDetailViewModel.update(note)
                    }
                }
            }
        }

        val editButton = findViewById<Button>(R.id.edit)
        editButton.setOnClickListener {
            val newIntent = Intent(this@NoteDetailActivity, NewNoteActivity::class.java)
            newIntent.putExtra("prevTitle", noteDetailViewModel.note.value?.title)
            newIntent.putExtra("prevText", noteDetailViewModel.note.value?.text)
            editNote.launch(newIntent)
        }

        val deleteButton = findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            val note = noteDetailViewModel.note.value
            if (note != null) {
                note.isDeleted = true
                noteDetailViewModel.update(note)
                finish()
            }
        }
    }
}