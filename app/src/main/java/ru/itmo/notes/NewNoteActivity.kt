package ru.itmo.notes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewNoteActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        val button = findViewById<Button>(R.id.button_save)

        val titleEditText = findViewById<EditText>(R.id.title)
        val prevTitle = intent.getStringExtra("prevTitle")
        titleEditText.setText(prevTitle)

        val textEditText = findViewById<EditText>(R.id.text)
        val prevText = intent.getStringExtra("prevText")
        textEditText.setText(prevText)
        textEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button.isEnabled = (s != null && s.trim().isNotEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        button.isEnabled = (textEditText.text.isNotEmpty())
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(textEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = titleEditText.text.toString().trim()
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