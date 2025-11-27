package com.example.devcatch.domain.repository

import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Category
import kotlinx.coroutines.flow.Flow

/**
 * 記事のRepository
 */
interface ArticleRepository {

    // ========== 取得 ==========

    /**
     * すべての記事を取得（新しい順）
     */
    fun getAllArticles(): Flow<List<Article>>

    /**
     * AI分析付きですべての記事を取得
     */
    fun getAllArticlesWithAnalysis(): Flow<List<ArticleWithAnalysis>>

    /**
     * カテゴリ別に記事を取得
     */
    fun getArticlesByCategory(category: Category): Flow<List<Article>>

    /**
     * カテゴリ別にAI分析付き記事を取得
     */
    fun getArticlesByCategoryWithAnalysis(category: Category): Flow<List<ArticleWithAnalysis>>

    /**
     * ブックマークされた記事を取得
     */
    fun getBookmarkedArticles(): Flow<List<Article>>

    /**
     * ブックマークされた記事（AI分析付き）を取得
     */
    fun getBookmarkedArticlesWithAnalysis(): Flow<List<ArticleWithAnalysis>>

    /**
     * 未読記事を取得
     */
    fun getUnreadArticles(): Flow<List<Article>>

    /**
     * 未読記事（AI分析付き）を取得
     */
    fun getUnreadArticlesWithAnalysis(): Flow<List<ArticleWithAnalysis>>

    /**
     * IDで記事を取得
     */
    suspend fun getArticleById(articleId: String): Article?

    /**
     * IDでAI分析付き記事を取得
     */
    suspend fun getArticleWithAnalysisById(articleId: String): ArticleWithAnalysis?

    /**
     * IDで記事を取得（Flow）
     */
    fun getArticleByIdFlow(articleId: String): Flow<Article?>

    /**
     * IDでAI分析付き記事を取得（Flow）
     */
    fun getArticleWithAnalysisByIdFlow(articleId: String): Flow<ArticleWithAnalysis?>

    // ========== 検索 ==========

    /**
     * 記事を検索（タイトルまたは内容）
     */
    fun searchArticles(query: String): Flow<List<Article>>

    /**
     * AI分析付きで記事を検索（タイトルまたは内容）
     */
    fun searchArticlesWithAnalysis(query: String): Flow<List<ArticleWithAnalysis>>

    /**
     * ソース別に記事を取得
     */
    fun getArticlesBySource(source: String): Flow<List<Article>>

    // ========== 挿入・更新 ==========

    /**
     * 記事を追加
     */
    suspend fun insertArticle(article: Article)

    /**
     * 複数の記事を追加
     */
    suspend fun insertArticles(articles: List<Article>)

    /**
     * 記事を更新
     */
    suspend fun updateArticle(article: Article)

    /**
     * ブックマーク状態を更新
     */
    suspend fun updateBookmarkStatus(articleId: String, isBookmarked: Boolean)

    /**
     * 既読状態を更新
     */
    suspend fun updateReadStatus(articleId: String, isRead: Boolean)

    // ========== 削除 ==========

    /**
     * 記事を削除
     */
    suspend fun deleteArticle(article: Article)

    /**
     * IDで記事を削除
     */
    suspend fun deleteArticleById(articleId: String)

    /**
     * 古い記事を削除（指定日時より前、ブックマーク以外）
     */
    suspend fun deleteOldArticles(timestamp: Long)

    // ========== 統計 ==========

    /**
     * 記事の総数を取得
     */
    suspend fun getArticleCount(): Int

    /**
     * カテゴリ別の記事数を取得
     */
    suspend fun getArticleCountByCategory(category: Category): Int

    /**
     * 未読記事数を取得
     */
    fun getUnreadCount(): Flow<Int>
}