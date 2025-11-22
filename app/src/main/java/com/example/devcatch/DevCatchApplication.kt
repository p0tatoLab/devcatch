package com.example.devcatch

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DevCatchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // アプリ起動時の初期化処理
    }
}