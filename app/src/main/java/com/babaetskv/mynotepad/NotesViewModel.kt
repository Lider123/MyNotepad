package com.babaetskv.mynotepad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.babaetskv.mynotepad.data.AppDatabase
import com.babaetskv.mynotepad.data.Note
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author babaetskv on 12.12.19
 */
class NotesViewModel(private val database: AppDatabase) : ViewModel() {

    private var disposable: Disposable? = null

    val notesLiveData = MutableLiveData<List<Note>>()

    fun getNotes() {
        disposable = database.noteDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { notes ->
                notesLiveData.postValue(notes)
            }
    }

    fun addNote(note: Note) {
        database.noteDao().insert(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: CompletableObserver {

                override fun onComplete() {
                    val currNotes = notesLiveData.value as ArrayList
                    currNotes.add(note)
                    notesLiveData.postValue(currNotes)
                }

                override fun onSubscribe(d: Disposable) = Unit

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun updateNote(note: Note) {
        database.noteDao().update(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {

                override fun onComplete() {
                    val currNotes = notesLiveData.value as ArrayList
                    currNotes.map { if (it.id == note.id) note else it }.let {
                        notesLiveData.postValue(it)
                    }
                }

                override fun onSubscribe(d: Disposable) = Unit

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun deleteNote(note: Note) {
        database.noteDao().delete(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {

                override fun onComplete() {
                    val currNotes = notesLiveData.value as ArrayList
                    currNotes.filter { it.id != note.id }.let {
                        notesLiveData.postValue(it)
                    }
                }

                override fun onSubscribe(d: Disposable) = Unit

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    fun stop() {
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    class Factory(private val database: AppDatabase): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>) = NotesViewModel(database) as T
    }
}