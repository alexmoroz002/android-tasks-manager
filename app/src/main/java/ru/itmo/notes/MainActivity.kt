package ru.itmo.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.itmo.notes.adapters.FolderListAdapter
import ru.itmo.notes.callbacks.FolderCallback
import ru.itmo.notes.entities.Folder
import ru.itmo.notes.viewmodels.FoldersViewModel
import ru.itmo.notes.viewmodels.FoldersViewModelFactory

class MainActivity : AppCompatActivity() {
    private val foldersViewModel: FoldersViewModel by viewModels {
        FoldersViewModelFactory((application as NotesApplication).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.folders_list)
        val adapter = FolderListAdapter(object : FolderCallback {
            override fun onClick(folder: Folder) {
                val intent = Intent(this@MainActivity, NotesActivity::class.java)
                intent.putExtra("folderId", folder.id)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val createFolder = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.getStringExtra(NewFolderActivity.EXTRA_REPLY)?.let { reply ->
                    val folder = Folder(reply)
                    foldersViewModel.insert(folder)
                }
            }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewFolderActivity::class.java)
            createFolder.launch(intent)
        }

        val settingsButton = findViewById<Button>(R.id.settings)
        settingsButton.setOnClickListener {
            val newIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(newIntent)
        }

        foldersViewModel.folders.observe(this) { folders ->
            folders?.let { adapter.submitList(it) }
        }
    }
}