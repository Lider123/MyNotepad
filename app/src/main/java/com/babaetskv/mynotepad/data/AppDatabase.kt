package com.babaetskv.mynotepad.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author babaetskv on 10.12.19
 */
@Database(entities = [ Note::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
