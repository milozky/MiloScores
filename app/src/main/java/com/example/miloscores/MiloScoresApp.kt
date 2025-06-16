package com.example.miloscores

import android.app.Application
import com.example.miloscores.di.appModule
import com.example.miloscores.di.dataModule
import com.example.miloscores.di.networkModule
import com.example.miloscores.di.repositoryModule
import com.example.miloscores.di.useCaseModule
import com.example.miloscores.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MiloScoresApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@MiloScoresApp)
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    repositoryModule,
                    useCaseModule,
                    dataModule,
                    networkModule
                )
            )
        }
    }
} 