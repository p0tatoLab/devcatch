package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 記事を検索するUseCase
 */
class SearchArticlesUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    /**
     * 記事を検索（タイトルまたは内容）
     */
    operator fun invoke(query: String): Flow<List<Article>> {
        return if (query.isBlank()) {
            repository.getAllArticles()
        } else {
            repository.searchArticles(query)
        }
    }
}