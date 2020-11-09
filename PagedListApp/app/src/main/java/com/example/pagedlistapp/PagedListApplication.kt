package com.example.pagedlistapp

import android.app.Application
import com.example.pagedlistapp.utils.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PagedListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PagedListApplication)
            modules(appModule)
        }
    }
}