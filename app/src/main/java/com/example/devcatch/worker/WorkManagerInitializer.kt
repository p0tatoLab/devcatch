package com.example.devcatch.worker

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * WorkManagerの初期化
 */
@Singleton
class WorkManagerInitializer @Inject constructor(
    private val workManager: WorkManager,
    @Named("FetchArticlesWork") private val fetchArticlesWork: PeriodicWorkRequest,
    @Named("CleanupWork") private val cleanupWork: PeriodicWorkRequest
) {

    /**
     * 定期的なWorkをスケジュール
     */
    fun schedulePeriodicWork() {
        // 記事取得Work
        workManager.enqueueUniquePeriodicWork(
            FetchArticlesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // 既存のWorkがあれば保持
            fetchArticlesWork
        )
        Log.d(TAG, "Scheduled FetchArticlesWorker")

        // クリーンアップWork
        workManager.enqueueUniquePeriodicWork(
            CleanupWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            cleanupWork
        )
        Log.d(TAG, "Scheduled CleanupWorker")
    }

    /**
     * すべてのWorkをキャンセル
     */
    fun cancelAllWork() {
        workManager.cancelUniqueWork(FetchArticlesWorker.WORK_NAME)
        workManager.cancelUniqueWork(CleanupWorker.WORK_NAME)
        Log.d(TAG, "Cancelled all work")
    }

    companion object {
        private const val TAG = "WorkManagerInitializer"
    }
}