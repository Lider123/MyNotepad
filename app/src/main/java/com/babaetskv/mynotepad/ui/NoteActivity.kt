package com.babaetskv.mynotepad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.Note
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

class NoteActivity : AppCompatActivity() {
    private var createMode: Boolean = false
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        fab_submit.setOnClickListener {
            if (createMode) {
                TitleDialog(this) { title ->
                    val note = Note(
                        title,
                        note_text.text.toString(),
                        Date().time
                    )
                    handleSubmit(note)
                }.show()
            } else {
                note!!.text = note_text.text.toString()
                note!!.updatedAt = Date().time
                handleSubmit(note!!)
            }
        }

        note = intent.extras?.getSerializable(EXTRA_NOTE) as Note?
        note?.let {
            createMode = false
            note_text.setText(note!!.text)
        } ?: run {
            createMode = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }

    private fun handleSubmit(note: Note) {
        val intent = Intent()
        intent.putExtra(EXTRA_NOTE, note)
        setResult(RESULT_OK, intent)
        finish()
    }

    companion object {
        const val EXTRA_NOTE = "note"
    }
}
