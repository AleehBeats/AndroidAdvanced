package com.example.mvvm

import android.app.Application
import com.example.mvvm.utils.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AnimeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AnimeApplication)
            modules(appModule)
        }
    }
}