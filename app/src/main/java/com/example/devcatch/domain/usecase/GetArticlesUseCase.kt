package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 記事を取得するUseCase
 */
class GetArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    /**
     * すべての記事を取得
     */
    operator fun invoke(): Flow<List<Article>> {
        return repository.getAllArticles()
    }

    /**
     * AI分析付きですべての記事を取得
     */
    fun withAnalysis(): Flow<List<ArticleWithAnalysis>> {
        return repository.getAllArticlesWithAnalysis()
    }

    /**
     * カテゴリ別に記事を取得
     */
    fun byCategory(category: Category): Flow<List<Article>> {
        return repository.getArticlesByCategory(category)
    }

    /**
     * カテゴリ別にAI分析付き記事を取得
     */
    fun byCategoryWithAnalysis(category: Category): Flow<List<ArticleWithAnalysis>> {
        return repository.getArticlesByCategoryWithAnalysis(category)
    }

    /**
     * ブックマークされた記事を取得
     */
    fun bookmarked(): Flow<List<Article>> {
        return repository.getBookmarkedArticles()
    }

    /**
     * ブックマークされた記事（AI分析付き）を取得
     */
    fun bookmarkedWithAnalysis(): Flow<List<ArticleWithAnalysis>> {
        return repository.getBookmarkedArticlesWithAnalysis()
    }

    /**
     * 未読記事を取得
     */
    fun unread(): Flow<List<Article>> {
        return repository.getUnreadArticles()
    }

    /**
     * 未読記事（AI分析付き）を取得
     */
    fun unreadWithAnalysis(): Flow<List<ArticleWithAnalysis>> {
        return repository.getUnreadArticlesWithAnalysis()
    }
}