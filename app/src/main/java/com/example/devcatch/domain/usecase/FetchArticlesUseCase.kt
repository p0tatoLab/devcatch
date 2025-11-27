package com.example.devcatch.domain.usecase

import com.example.devcatch.data.remote.mapper.RssMapper
import com.example.devcatch.data.remote.parser.RssFeedParser
import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.NewsSource
import com.example.devcatch.domain.model.SourceType
import com.example.devcatch.domain.repository.ArticleRepository
import com.example.devcatch.domain.repository.NewsSourceRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * ニュースソースから記事を取得するUseCase
 */
class FetchArticlesUseCase @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository,
    private val articleRepository: ArticleRepository,
    private val rssFeedParser: RssFeedParser
) {

    /**
     * すべての有効なソースから記事を取得
     */
    suspend operator fun invoke(): Result<Int> {
        return try {
            val sources = newsSourceRepository.getEnabledSources().first()
            var totalArticles = 0

            sources.forEach { source ->
                when (source.type) {
                    SourceType.RSS -> {
                        val result = fetchFromRss(source)
                        result.getOrNull()?.let { count ->
                            totalArticles += count
                        }
                    }
                    SourceType.API -> {
                        // 今後実装（GitHub API等）
                    }
                    SourceType.SCRAPING -> {
                        // 今後実装（Webスクレイピング）
                    }
                }
            }

            Result.success(totalArticles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * RSSソースから記事を取得
     */
    private suspend fun fetchFromRss(source: NewsSource): Result<Int> {
        return try {
            val result = rssFeedParser.parseRssFeed(source.url)

            result.fold(
                onSuccess = { rssItems ->
                    val articles = rssItems.map { dto ->
                        RssMapper.toArticle(
                            dto = dto,
                            sourceName = source.name,
                            defaultCategory = source.category
                        )
                    }

                    // データベースに保存
                    articleRepository.insertArticles(articles)

                    Result.success(articles.size)
                },
                onFailure = { exception ->
                    Result.failure(exception)
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}