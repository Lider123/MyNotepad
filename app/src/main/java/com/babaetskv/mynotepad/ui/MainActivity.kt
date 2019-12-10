package com.babaetskv.mynotepad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babaetskv.mynotepad.MainApplication
import com.babaetskv.mynotepad.adapter.NoteAdapter
import com.babaetskv.mynotepad.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTES_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                loadNotes()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivityForResult(intent,
                NOTES_REQUEST_CODE
            )
        }

        loadNotes()
    }

    private fun loadNotes() {
        val notes = MainApplication.instance.database.noteDao().getAll()
        val adapter = NoteAdapter(this, notes)
        notes_list.adapter = adapter
    }

    companion object {
        private const val NOTES_REQUEST_CODE = 101
    }
}
