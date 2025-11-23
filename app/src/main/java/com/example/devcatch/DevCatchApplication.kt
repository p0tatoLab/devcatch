package com.example.devcatch

import android.app.Application
import com.example.devcatch.data.initializer.DataInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class DevCatchApplication : Application() {

    @Inject
    lateinit var dataInitializer: DataInitializer

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // 初期データをセットアップ
        applicationScope.launch {
            dataInitializer.initialize()
        }
    }
}