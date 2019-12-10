package com.babaetskv.mynotepad.data

import androidx.room.*
import com.babaetskv.mynotepad.data.Note

/**
 * @author babaetskv on 10.12.19
 */
@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getById(id: String): Note

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}
