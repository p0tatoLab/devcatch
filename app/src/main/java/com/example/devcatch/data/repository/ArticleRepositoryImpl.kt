package com.example.devcatch.data.repository

import com.example.devcatch.data.local.dao.ArticleAnalysisDao
import com.example.devcatch.data.local.dao.ArticleDao
import com.example.devcatch.data.local.entity.toEntity
import com.example.devcatch.data.local.entity.toDomainModel
import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ArticleRepositoryの実装
 */
@Singleton
class ArticleRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val analysisDao: ArticleAnalysisDao
) : ArticleRepository {

    // ========== 取得 ==========

    override fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getAllArticlesWithAnalysis(): Flow<List<ArticleWithAnalysis>> {
        return articleDao.getAllArticles().map { articleEntities ->
            articleEntities.map { articleEntity ->
                val article = articleEntity.toDomainModel()
                val analysis = analysisDao.getAnalysisByArticleId(articleEntity.id)?.toDomainModel()
                ArticleWithAnalysis(article, analysis)
            }
        }
    }

    override fun getArticlesByCategory(category: Category): Flow<List<Article>> {
        return articleDao.getArticlesByCategory(category.name).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getArticlesByCategoryWithAnalysis(category: Category): Flow<List<ArticleWithAnalysis>> {
        return articleDao.getArticlesByCategory(category.name).map { articleEntities ->
            articleEntities.map { articleEntity ->
                val article = articleEntity.toDomainModel()
                val analysis = analysisDao.getAnalysisByArticleId(articleEntity.id)?.toDomainModel()
                ArticleWithAnalysis(article, analysis)
            }
        }
    }

    override fun getBookmarkedArticles(): Flow<List<Article>> {
        return articleDao.getBookmarkedArticles().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getBookmarkedArticlesWithAnalysis(): Flow<List<ArticleWithAnalysis>> {
        return articleDao.getBookmarkedArticles().map { articleEntities ->
            articleEntities.map { articleEntity ->
                val article = articleEntity.toDomainModel()
                val analysis = analysisDao.getAnalysisByArticleId(articleEntity.id)?.toDomainModel()
                ArticleWithAnalysis(article, analysis)
            }
        }
    }

    override fun getUnreadArticles(): Flow<List<Article>> {
        return articleDao.getUnreadArticles().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getUnreadArticlesWithAnalysis(): Flow<List<ArticleWithAnalysis>> {
        return articleDao.getUnreadArticles().map { articleEntities ->
            articleEntities.map { articleEntity ->
                val article = articleEntity.toDomainModel()
                val analysis = analysisDao.getAnalysisByArticleId(articleEntity.id)?.toDomainModel()
                ArticleWithAnalysis(article, analysis)
            }
        }
    }

    override suspend fun getArticleById(articleId: String): Article? {
        return articleDao.getArticleById(articleId)?.toDomainModel()
    }

    override suspend fun getArticleWithAnalysisById(articleId: String): ArticleWithAnalysis? {
        val articleEntity = articleDao.getArticleById(articleId) ?: return null
        val article = articleEntity.toDomainModel()
        val analysis = analysisDao.getAnalysisByArticleId(articleId)?.toDomainModel()
        return ArticleWithAnalysis(article, analysis)
    }

    override fun getArticleByIdFlow(articleId: String): Flow<Article?> {
        return articleDao.getArticleByIdFlow(articleId).map { entity ->
            entity?.toDomainModel()
        }
    }

    override fun getArticleWithAnalysisByIdFlow(articleId: String): Flow<ArticleWithAnalysis?> {
        return combine(
            articleDao.getArticleByIdFlow(articleId),
            analysisDao.getAnalysisByArticleIdFlow(articleId)
        ) { articleEntity, analysisEntity ->
            if (articleEntity != null) {
                val article = articleEntity.toDomainModel()
                val analysis = analysisEntity?.toDomainModel()
                ArticleWithAnalysis(article, analysis)
            } else {
                null
            }
        }
    }

    // ========== 検索 ==========

    override fun searchArticles(query: String): Flow<List<Article>> {
        return articleDao.searchArticles(query).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getArticlesBySource(source: String): Flow<List<Article>> {
        return articleDao.getArticlesBySource(source).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    // ========== 挿入・更新 ==========

    override suspend fun insertArticle(article: Article) {
        articleDao.insertArticle(article.toEntity())
    }

    override suspend fun insertArticles(articles: List<Article>) {
        articleDao.insertArticles(articles.map { it.toEntity() })
    }

    override suspend fun updateArticle(article: Article) {
        articleDao.updateArticle(article.toEntity())
    }

    override suspend fun updateBookmarkStatus(articleId: String, isBookmarked: Boolean) {
        articleDao.updateBookmarkStatus(articleId, isBookmarked)
    }

    override suspend fun updateReadStatus(articleId: String, isRead: Boolean) {
        articleDao.updateReadStatus(articleId, isRead)
    }

    // ========== 削除 ==========

    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article.toEntity())
    }

    override suspend fun deleteArticleById(articleId: String) {
        articleDao.deleteArticleById(articleId)
    }

    override suspend fun deleteOldArticles(timestamp: Long) {
        articleDao.deleteOldArticles(timestamp)
    }

    // ========== 統計 ==========

    override suspend fun getArticleCount(): Int {
        return articleDao.getArticleCount()
    }

    override suspend fun getArticleCountByCategory(category: Category): Int {
        return articleDao.getArticleCountByCategory(category.name)
    }

    override fun getUnreadCount(): Flow<Int> {
        return articleDao.getUnreadCount()
    }
}