package com.example.kurippu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewNoteActivity : AppCompatActivity() {
    private lateinit var editNoteView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        editNoteView = findViewById(R.id.editNoteText)

        val button = findViewById<Button>(R.id.saveButton)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNoteView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editNoteView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

}