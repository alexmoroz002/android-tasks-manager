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

class NewFolderActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_folder)
        val button = findViewById<Button>(R.id.button_save)

        val editText = findViewById<EditText>(R.id.edit_word)
        val prevTitle = intent.getStringExtra("prevTitle")
        editText.setText(prevTitle)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                button.isEnabled = (s != null && s.trim().isNotEmpty())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        button.isEnabled = (editText.text.isNotEmpty())
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val title = editText.text.toString().trim()
                replyIntent.putExtra(EXTRA_REPLY, title)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "ru.itmo.notes.folder.REPLY"
    }
}