package com.example.devcatch.data.local.dao

import androidx.room.*
import com.example.devcatch.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    // ========== 取得 ==========

    /**
     * すべての記事を取得（新しい順）
     */
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    /**
     * カテゴリ別に記事を取得
     */
    @Query("SELECT * FROM articles WHERE category = :category ORDER BY publishedAt DESC")
    fun getArticlesByCategory(category: String): Flow<List<ArticleEntity>>

    /**
     * ブックマークされた記事を取得
     */
    @Query("SELECT * FROM articles WHERE isBookmarked = 1 ORDER BY publishedAt DESC")
    fun getBookmarkedArticles(): Flow<List<ArticleEntity>>

    /**
     * 未読記事を取得
     */
    @Query("SELECT * FROM articles WHERE isRead = 0 ORDER BY publishedAt DESC")
    fun getUnreadArticles(): Flow<List<ArticleEntity>>

    /**
     * IDで記事を取得
     */
    @Query("SELECT * FROM articles WHERE id = :articleId")
    suspend fun getArticleById(articleId: String): ArticleEntity?

    /**
     * IDで記事を取得（Flow）
     */
    @Query("SELECT * FROM articles WHERE id = :articleId")
    fun getArticleByIdFlow(articleId: String): Flow<ArticleEntity?>

    // ========== 検索 ==========

    /**
     * 記事を検索（タイトルまたは内容）
     */
    @Query("""
        SELECT * FROM articles 
        WHERE title LIKE '%' || :query || '%' 
        OR content LIKE '%' || :query || '%'
        ORDER BY publishedAt DESC
    """)
    fun searchArticles(query: String): Flow<List<ArticleEntity>>

    /**
     * ソース別に記事を取得
     */
    @Query("SELECT * FROM articles WHERE source = :source ORDER BY publishedAt DESC")
    fun getArticlesBySource(source: String): Flow<List<ArticleEntity>>

    // ========== 挿入・更新 ==========

    /**
     * 記事を挿入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleEntity)

    /**
     * 複数の記事を挿入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    /**
     * 記事を更新
     */
    @Update
    suspend fun updateArticle(article: ArticleEntity)

    /**
     * ブックマーク状態を更新
     */
    @Query("UPDATE articles SET isBookmarked = :isBookmarked WHERE id = :articleId")
    suspend fun updateBookmarkStatus(articleId: String, isBookmarked: Boolean)

    /**
     * 既読状態を更新
     */
    @Query("UPDATE articles SET isRead = :isRead WHERE id = :articleId")
    suspend fun updateReadStatus(articleId: String, isRead: Boolean)

    // ========== 削除 ==========

    /**
     * 記事を削除
     */
    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    /**
     * IDで記事を削除
     */
    @Query("DELETE FROM articles WHERE id = :articleId")
    suspend fun deleteArticleById(articleId: String)

    /**
     * すべての記事を削除
     */
    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    /**
     * 古い記事を削除（指定日時より前）
     */
    @Query("DELETE FROM articles WHERE createdAt < :timestamp AND isBookmarked = 0")
    suspend fun deleteOldArticles(timestamp: Long)

    // ========== 統計 ==========

    /**
     * 記事の総数を取得
     */
    @Query("SELECT COUNT(*) FROM articles")
    suspend fun getArticleCount(): Int

    /**
     * カテゴリ別の記事数を取得
     */
    @Query("SELECT COUNT(*) FROM articles WHERE category = :category")
    suspend fun getArticleCountByCategory(category: String): Int

    /**
     * 未読記事数を取得
     */
    @Query("SELECT COUNT(*) FROM articles WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>
}