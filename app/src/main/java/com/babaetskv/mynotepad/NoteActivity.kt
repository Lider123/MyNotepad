package com.babaetskv.mynotepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

class NoteActivity : AppCompatActivity() {
    private var createMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        fab_submit.setOnClickListener {
            TitleDialog(this) { title ->
                val note = Note(title, note_text.text.toString(), Date().time)
                if (createMode) {
                    MainApplication.instance.sharedPrefsHelper.createNote(note)
                } else MainApplication.instance.sharedPrefsHelper.updateNote(note)

                val intent = Intent()
                intent.putExtra(EXTRA_NOTE, note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }.show()
        }

        val note = intent.extras?.getSerializable(EXTRA_NOTE) as Note?
        note?.let {
            createMode = false
            note_text.setText(note.text)
        } ?: run {
            createMode = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }

    companion object {
        const val EXTRA_NOTE = "note"
    }
}
