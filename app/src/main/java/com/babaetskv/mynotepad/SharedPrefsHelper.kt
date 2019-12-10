package com.babaetskv.mynotepad

import android.content.Context
import com.google.gson.Gson
import java.util.*
import kotlin.collections.HashSet

/**
 * @author babaetskv on 10.12.19
 */
class SharedPrefsHelper(context: Context) : ISharedPrefsHelper {
    private val appPrefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun createNote(note: Note) {
        val notesJsons = appPrefs.getStringSet(PREF_NOTES, HashSet())
        notesJsons?.add(Gson().toJson(note))
        appPrefs.edit().putStringSet(PREF_NOTES, notesJsons).apply()
    }

    override fun getNotes(): List<Note> {
        val notesJsons = appPrefs.getStringSet(PREF_NOTES, null)
        notesJsons ?: return listOf()

        val notes = notesJsons.map { json -> Gson().fromJson(json, Note::class.java) }
        return notes
    }

    override fun updateNote(note: Note) {
        val notesJsons = appPrefs.getStringSet(PREF_NOTES, null)
        val currNote = notesJsons?.find { json -> json.contains(note.id.toString()) }
        if (currNote == null) createNote(note) else notesJsons.map { json ->
            if (json.contains(note.id.toString())) Gson().toJson(note) else json
        }
        appPrefs.edit().putStringSet(PREF_NOTES, notesJsons).apply()
    }

    override fun deleteNote(id: UUID) {
        val notesJsons = appPrefs.getStringSet(PREF_NOTES, setOf()) as Set<String>
        notesJsons.filter { json -> !json.contains(id.toString()) }
        appPrefs.edit().putStringSet(PREF_NOTES, notesJsons).apply()
    }

    companion object {
        private const val APP_PREFERENCES = "app_prefs"
        private const val PREF_NOTES = "notes"
    }
}