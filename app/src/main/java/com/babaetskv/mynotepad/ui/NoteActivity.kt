package com.babaetskv.mynotepad.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.babaetskv.mynotepad.MainApplication
import com.babaetskv.mynotepad.R
import com.babaetskv.mynotepad.data.Note
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
        setResult(Activity.RESULT_CANCELED)
    }

    private fun handleSubmit(note: Note) {
        Completable.fromAction {
            if (createMode) {
                MainApplication.instance.database.noteDao().insert(note)
            } else MainApplication.instance.database.noteDao().update(note)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: CompletableObserver {

                override fun onComplete() {
                    val intent = Intent()
                    intent.putExtra(EXTRA_NOTE, note)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }

                override fun onSubscribe(d: Disposable) = Unit

                override fun onError(e: Throwable) = Unit
            })
    }

    companion object {
        const val EXTRA_NOTE = "note"
    }
}
