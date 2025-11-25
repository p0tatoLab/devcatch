package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * 記事を既読にするUseCase
 */
class MarkAsReadUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    /**
     * 既読状態を更新
     */
    suspend operator fun invoke(articleId: String, isRead: Boolean = true) {
        repository.updateReadStatus(articleId, isRead)
    }

    /**
     * 既読にする
     */
    suspend fun markAsRead(articleId: String) {
        invoke(articleId, true)
    }

    /**
     * 未読にする
     */
    suspend fun markAsUnread(articleId: String) {
        invoke(articleId, false)
    }
}