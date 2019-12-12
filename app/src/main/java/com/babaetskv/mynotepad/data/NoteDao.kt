package com.babaetskv.mynotepad.data

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * @author babaetskv on 10.12.19
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): Single<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: String): Maybe<Note>

    @Insert
    fun insert(note: Note): Completable

    @Update
    fun update(note: Note): Completable

    @Delete
    fun delete(note: Note): Completable
}
