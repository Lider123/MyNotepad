package com.babaetskv.mynotepad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val notes = listOf(
        Note("Note 1", "Simple note", 0),
        Note("Note 2", "Another simple note", 5),
        Note("Note 3", "Yet another simple note", 4)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = NoteAdapter(this, notes)
        notes_list.adapter = adapter
    }
}
