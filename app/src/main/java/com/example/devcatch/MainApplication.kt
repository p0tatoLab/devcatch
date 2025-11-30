package com.example.devcatch

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.devcatch.data.initializer.DataInitializer
import com.example.devcatch.worker.WorkManagerInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var dataInitializer: DataInitializer

    @Inject
    lateinit var workManagerInitializer: WorkManagerInitializer

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // 初期データをセットアップ
        applicationScope.launch {
            dataInitializer.initialize()
        }

        // WorkManagerをスケジュール
        workManagerInitializer.schedulePeriodicWork()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}