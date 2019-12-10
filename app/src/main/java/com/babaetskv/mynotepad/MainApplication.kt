package com.babaetskv.mynotepad

import android.app.Application
import androidx.room.Room
import com.babaetskv.mynotepad.data.AppDatabase

/**
 * @author babaetskv on 10.12.19
 */
class MainApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MainApplication
    }
}