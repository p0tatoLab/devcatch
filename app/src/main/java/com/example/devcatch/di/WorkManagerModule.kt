package com.example.devcatch.di

import android.content.Context
import androidx.work.*
import com.example.devcatch.worker.CleanupWorker
import com.example.devcatch.worker.FetchArticlesWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    /**
     * WorkManager を提供
     */
    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

    /**
     * 定期的な記事取得Workをスケジュール
     */
    @Provides
    @Singleton
    @Named("FetchArticlesWork")
    fun provideFetchArticlesWork(): PeriodicWorkRequest {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        return PeriodicWorkRequestBuilder<FetchArticlesWorker>(
            repeatInterval = 6, // 6時間ごと
            repeatIntervalTimeUnit = TimeUnit.HOURS,
            flexTimeInterval = 1, // 1時間の柔軟性
            flexTimeIntervalUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }

    /**
     * 定期的なクリーンアップWorkをスケジュール
     */
    @Provides
    @Singleton
    @Named("CleanupWork")
    fun provideCleanupWork(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<CleanupWorker>(
            repeatInterval = 1, // 1日ごと
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
    }
}