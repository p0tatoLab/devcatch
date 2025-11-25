package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * IDで記事を取得するUseCase
 */
class GetArticleByIdUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    /**
     * IDで記事を取得
     */
    suspend operator fun invoke(articleId: String): Article? {
        return repository.getArticleById(articleId)
    }

    /**
     * IDでAI分析付き記事を取得
     */
    suspend fun withAnalysis(articleId: String): ArticleWithAnalysis? {
        return repository.getArticleWithAnalysisById(articleId)
    }

    /**
     * IDで記事を取得（Flow）
     */
    fun asFlow(articleId: String): Flow<Article?> {
        return repository.getArticleByIdFlow(articleId)
    }

    /**
     * IDでAI分析付き記事を取得（Flow）
     */
    fun withAnalysisAsFlow(articleId: String): Flow<ArticleWithAnalysis?> {
        return repository.getArticleWithAnalysisByIdFlow(articleId)
    }
}