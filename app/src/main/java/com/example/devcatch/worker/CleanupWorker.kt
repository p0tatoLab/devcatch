package com.example.devcatch.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.devcatch.domain.usecase.DeleteOldArticlesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * 古い記事を削除するWorker
 */
@HiltWorker
class CleanupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val deleteOldArticlesUseCase: DeleteOldArticlesUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting cleanup...")

            // 30日以上前の記事を削除
            deleteOldArticlesUseCase(daysOld = 30)

            Log.d(TAG, "Cleanup completed successfully")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error in cleanup", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "CleanupWorker"
        const val WORK_NAME = "cleanup_work"
    }
}