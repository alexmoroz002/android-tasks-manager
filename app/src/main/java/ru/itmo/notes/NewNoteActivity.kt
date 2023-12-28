package ru.itmo.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewNoteActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        val titleEditText = findViewById<EditText>(R.id.title)
        val textEditText = findViewById<EditText>(R.id.text)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(titleEditText.text) || TextUtils.isEmpty(textEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = titleEditText.text.toString()
                val text = textEditText.text.toString()
                replyIntent.putExtra(TITLE_REPLY, title)
                replyIntent.putExtra(TEXT_REPLY, text)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val TITLE_REPLY = "ru.itmo.notes.note.TITLE_REPLY"
        const val TEXT_REPLY = "ru.itmo.notes.note.TEXT_REPLY"
    }
}