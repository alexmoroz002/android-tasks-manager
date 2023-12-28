package ru.itmo.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.itmo.notes.adapters.FolderListAdapter
import ru.itmo.notes.callbacks.FolderCallback
import ru.itmo.notes.entities.Folder
import ru.itmo.notes.viewmodels.RestoreFoldersVM
import ru.itmo.notes.viewmodels.RestoreFoldersVMFactory

class RestoreFoldersActivity : AppCompatActivity() {
    private val foldersViewModel: RestoreFoldersVM by viewModels {
        RestoreFoldersVMFactory((application as NotesApplication).repo)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_folders)

        val recyclerView = findViewById<RecyclerView>(R.id.folders_list)
        val adapter = FolderListAdapter(object : FolderCallback {
            override fun onClick(folder: Folder) {
                foldersViewModel.restore(folder)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        foldersViewModel.folders.observe(this) { folders ->
            folders?.let { adapter.submitList(it) }
        }

    }
}