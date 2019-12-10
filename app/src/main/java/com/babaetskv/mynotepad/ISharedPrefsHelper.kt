package com.babaetskv.mynotepad

import java.util.*

/**
 * @author babaetskv on 10.12.19
 */
interface ISharedPrefsHelper {
    fun createNote(note: Note)
    fun getNotes(): List<Note>
    fun updateNote(note: Note)
    fun deleteNote(id: UUID)
}