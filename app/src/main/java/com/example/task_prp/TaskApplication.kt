package com.example.task_prp

import android.app.Application
import com.example.task_prp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class TaskApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@TaskApplication)
            modules(appModule)
        }
    }
}