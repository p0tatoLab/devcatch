package com.example.devcatch.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.devcatch.domain.usecase.FetchArticlesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * 記事を取得するWorker
 */
@HiltWorker
class FetchArticlesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val fetchArticlesUseCase: FetchArticlesUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting article fetch...")

            val result = fetchArticlesUseCase()

            result.fold(
                onSuccess = { count ->
                    Log.d(TAG, "Successfully fetched $count articles")
                    Result.success()
                },
                onFailure = { exception ->
                    Log.e(TAG, "Failed to fetch articles", exception)
                    Result.retry()
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error in doWork", e)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "FetchArticlesWorker"
        const val WORK_NAME = "fetch_articles_work"
    }
}