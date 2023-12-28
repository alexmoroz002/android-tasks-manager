package ru.itmo.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.itmo.notes.adapters.NoteListAdapter
import ru.itmo.notes.models.Note

class NotesActivity : AppCompatActivity() {
    private var folderId: Int = 1
    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as NotesApplication).repo, folderId)
    }
    private val newNoteRequestCode = 1
    private val editFolderRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        folderId = intent.getIntExtra("folderId", 1)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_list)
        val adapter = NoteListAdapter(object : NoteCallback {
            override fun onClick(note: Note) {
                val intent = Intent(this@NotesActivity, NoteDetailActivity::class.java)
                intent.putExtra("noteId", note.id)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@NotesActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, newNoteRequestCode)
        }

        val editButton = findViewById<Button>(R.id.edit)
        editButton.setOnClickListener {
            val newIntent = Intent(this@NotesActivity, NewFolderActivity::class.java)
            startActivityForResult(newIntent, editFolderRequestCode)
        }

        val deleteButton = findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            val folder = notesViewModel.folder.value
            if (folder != null) {
                folder.isDeleted = true
                notesViewModel.update(folder)
                finish()
            }
        }

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val title = findViewById<TextView>(R.id.title)
        notesViewModel.folder.observe(this) {
            title.text = it.title
        }

        notesViewModel.notes.observe(this) { notes ->
            notes?.let { adapter.submitList(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == editFolderRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewFolderActivity.EXTRA_REPLY)?.let { reply ->
                val folder = notesViewModel.folder.value
                if (folder != null) {
                    folder.title = reply
                    notesViewModel.update(folder)
                }
            }
        } else if (requestCode == newNoteRequestCode && resultCode == Activity.RESULT_OK) {
            val title = intentData?.getStringExtra(NewNoteActivity.TITLE_REPLY)
            val text = intentData?.getStringExtra(NewNoteActivity.TEXT_REPLY)
            if (title != null && text != null) {
                val note = Note(title, text, folderId)
                notesViewModel.insert(note)
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