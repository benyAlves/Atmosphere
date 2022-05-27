package com.maluleque.bernardo.atmosphere

import android.app.Application
import com.maluleque.bernardo.atmosphere.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Atmosphere : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Atmosphere)
            modules(appModule)
        }
    }
}