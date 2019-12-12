package com.babaetskv.mynotepad.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.room.Room
import com.babaetskv.mynotepad.NotesViewModel
import com.babaetskv.mynotepad.adapter.NoteAdapter
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.AppDatabase
import com.babaetskv.mynotepad.data.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NoteAdapter
    private lateinit var model: NotesViewModel

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.extras?.getSerializable(NoteActivity.EXTRA_NOTE)?.let {
                    it as Note
                    model.addNote(it)
                }
            }
        } else if (requestCode == UPDATE_NOTE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.extras?.getSerializable(NoteActivity.EXTRA_NOTE)?.let {
                    it as Note
                    model.updateNote(it)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()
        fab_add.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivityForResult(intent, CREATE_NOTE_REQUEST_CODE)
        }
    }

    override fun onStart() {
        super.onStart()
        val database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
        val factory = NotesViewModel.Factory(database)
        model = ViewModelProviders.of(this, factory).get(NotesViewModel::class.java)
        model.notesLiveData.observe(this, Observer { notes ->
            adapter.setItems(notes)
        })
        model.getNotes()
    }

    override fun onStop() {
        super.onStop()
        model.stop()
    }

    private fun onNoteClick(note: Note) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(NoteActivity.EXTRA_NOTE, note)
        startActivityForResult(intent, UPDATE_NOTE_REQUEST_CODE)
    }

    private fun onNoteLongClick(note: Note) {
        AlertDialog.Builder(this)
            .setTitle(R.string.dialog_deletion_title)
            .setMessage(R.string.dialog_deletion_message)
            .setPositiveButton(R.string.yes) { dialog, _ ->
                model.deleteNote(note)
                dialog.cancel()
            }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun initList() {
        adapter = NoteAdapter(this, listOf(), this::onNoteClick, this::onNoteLongClick)
        notes_list.adapter = adapter
    }

    companion object {
        private const val CREATE_NOTE_REQUEST_CODE = 101
        private const val UPDATE_NOTE_REQUEST_CODE = 102
    }
}
