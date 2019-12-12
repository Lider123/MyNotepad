package com.babaetskv.mynotepad.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.babaetskv.mynotepad.MainApplication
import com.babaetskv.mynotepad.adapter.NoteAdapter
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.Note
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NoteAdapter

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTES_REQUEST_CODE) {
            if (resultCode == RESULT_OK) loadNotes()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()
        fab_add.setOnClickListener {
            val intent = Intent(this, NoteActivity::class.java)
            startActivityForResult(intent, NOTES_REQUEST_CODE)
        }

        loadNotes()
    }

    private fun onNoteClick(note: Note) {
        val intent = Intent(this, NoteActivity::class.java)
        intent.putExtra(NoteActivity.EXTRA_NOTE, note)
        startActivityForResult(intent, NOTES_REQUEST_CODE)
    }

    private fun onNoteLongClick(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete note?")
            .setPositiveButton("Yes") { dialog, p1 ->
                MainApplication.instance.database.noteDao().delete(note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        dialog?.cancel()
                        loadNotes()
                    }
            }
            .setNegativeButton("No") { dialog, p1 -> dialog?.cancel() }
            .show()
    }

    private fun initList() {
        adapter = NoteAdapter(this, listOf(), this::onNoteClick, this::onNoteLongClick)
        notes_list.adapter = adapter
    }

    private fun loadNotes() {
        MainApplication.instance.database.noteDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notes ->
                adapter.setItems(notes)
            }
    }

    companion object {
        private const val NOTES_REQUEST_CODE = 101
    }
}
