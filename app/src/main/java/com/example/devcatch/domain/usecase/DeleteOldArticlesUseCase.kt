package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.repository.ArticleRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * 古い記事を削除するUseCase
 */
class DeleteOldArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    /**
     * 指定日数より古い記事を削除（ブックマーク以外）
     */
    suspend operator fun invoke(daysOld: Int = 30) {
        val timestamp = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(daysOld.toLong())
        repository.deleteOldArticles(timestamp)
    }
}