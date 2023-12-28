package ru.itmo.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val restoreFoldersCard = findViewById<MaterialCardView>(R.id.restore_folders)
        restoreFoldersCard.setOnClickListener {
            val newIntent = Intent(this@SettingsActivity, RestoreFoldersActivity::class.java)
            startActivity(newIntent)
        }
        val restoreNotesCard = findViewById<MaterialCardView>(R.id.restore_notes)
        restoreNotesCard.setOnClickListener {
            val newIntent = Intent(this@SettingsActivity, RestoreNotesActivity::class.java)
            startActivity(newIntent)
        }
        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            finish()
        }
    }
}