package ru.itmo.notes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.itmo.notes.models.Note

class NoteDetailActivity : AppCompatActivity() {
    private var noteId: Int = 1
    private val noteDetailViewModel: NoteDetailViewModel by viewModels {
        NoteDetailViewModelFactory((application as NotesApplication).repo, noteId)
    }
    private val editNoteRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        noteId = intent.getIntExtra("noteId", 1)
        val title = findViewById<TextView>(R.id.title)
        val text = findViewById<TextView>(R.id.text)

        noteDetailViewModel.note.observe(this) {
            title.text = it.title
            text.text = it.text
        }

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val editButton = findViewById<Button>(R.id.edit)
        editButton.setOnClickListener {
            val newIntent = Intent(this@NoteDetailActivity, NewNoteActivity::class.java)
            startActivityForResult(newIntent, editNoteRequestCode)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == editNoteRequestCode && resultCode == RESULT_OK) {
            val title = intentData?.getStringExtra(NewNoteActivity.TITLE_REPLY)
            val text = intentData?.getStringExtra(NewNoteActivity.TEXT_REPLY)
            if (title != null && text != null) {
                val note = noteDetailViewModel.note.value
                if (note != null) {
                    note.title = title
                    note.text = text
                    noteDetailViewModel.update(note)
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Action aborted",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}