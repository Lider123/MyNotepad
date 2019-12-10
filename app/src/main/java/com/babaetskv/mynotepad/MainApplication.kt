package com.babaetskv.mynotepad

import android.app.Application

/**
 * @author babaetskv on 10.12.19
 */
class MainApplication : Application() {
    val sharedPrefsHelper: ISharedPrefsHelper by lazy {
        SharedPrefsHelper(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MainApplication
    }
}