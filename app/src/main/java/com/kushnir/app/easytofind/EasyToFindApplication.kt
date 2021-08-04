package com.kushnir.app.easytofind

import android.app.Application
import com.kushnir.app.easytofind.di.appComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class EasyToFindApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLoggers()
    }

    private fun initKoin() {
        startKoin {

            androidLogger(level = Level.ERROR)
            androidContext(this@EasyToFindApplication)

            modules(appComponent)
        }
    }

    private fun initLoggers() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .tag("EasyToFindLogTag")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })
    }
}