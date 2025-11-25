package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.repository.ArticleRepository
import javax.inject.Inject

/**
 * 記事をブックマークするUseCase
 */
class BookmarkArticleUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    /**
     * ブックマーク状態を更新
     */
    suspend operator fun invoke(articleId: String, isBookmarked: Boolean) {
        repository.updateBookmarkStatus(articleId, isBookmarked)
    }

    /**
     * ブックマークを追加
     */
    suspend fun add(articleId: String) {
        invoke(articleId, true)
    }

    /**
     * ブックマークを削除
     */
    suspend fun remove(articleId: String) {
        invoke(articleId, false)
    }

    /**
     * ブックマーク状態を切り替え
     */
    suspend fun toggle(articleId: String) {
        val article = repository.getArticleById(articleId)
        if (article != null) {
            invoke(articleId, !article.isBookmarked)
        }
    }
}