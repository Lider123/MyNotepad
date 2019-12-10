package com.babaetskv.mynotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        intent.extras?.let {
            val note = it.getSerializable(EXTRA_NOTE) as Note
            note_text.setText(note.text)
        }
    }

    companion object {
        const val EXTRA_NOTE = "note"
    }
}
