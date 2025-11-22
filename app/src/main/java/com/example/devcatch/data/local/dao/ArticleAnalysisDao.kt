package com.example.devcatch.data.local.dao

import androidx.room.*
import com.example.devcatch.data.local.entity.ArticleAnalysisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleAnalysisDao {

    /**
     * 記事IDでAI分析を取得
     */
    @Query("SELECT * FROM article_analysis WHERE articleId = :articleId")
    suspend fun getAnalysisByArticleId(articleId: String): ArticleAnalysisEntity?

    /**
     * 記事IDでAI分析を取得（Flow）
     */
    @Query("SELECT * FROM article_analysis WHERE articleId = :articleId")
    fun getAnalysisByArticleIdFlow(articleId: String): Flow<ArticleAnalysisEntity?>

    /**
     * すべてのAI分析を取得
     */
    @Query("SELECT * FROM article_analysis ORDER BY analyzedAt DESC")
    fun getAllAnalysis(): Flow<List<ArticleAnalysisEntity>>

    /**
     * 重要度スコアでフィルター
     */
    @Query("SELECT * FROM article_analysis WHERE relevanceScore >= :minScore ORDER BY relevanceScore DESC")
    fun getAnalysisByMinScore(minScore: Int): Flow<List<ArticleAnalysisEntity>>

    /**
     * 難易度でフィルター
     */
    @Query("SELECT * FROM article_analysis WHERE difficulty = :difficulty")
    fun getAnalysisByDifficulty(difficulty: String): Flow<List<ArticleAnalysisEntity>>

    /**
     * AI分析を挿入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: ArticleAnalysisEntity)

    /**
     * 複数のAI分析を挿入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalyses(analyses: List<ArticleAnalysisEntity>)

    /**
     * AI分析を更新
     */
    @Update
    suspend fun updateAnalysis(analysis: ArticleAnalysisEntity)

    /**
     * AI分析を削除
     */
    @Query("DELETE FROM article_analysis WHERE articleId = :articleId")
    suspend fun deleteAnalysisByArticleId(articleId: String)

    /**
     * 古いAI分析を削除
     */
    @Query("DELETE FROM article_analysis WHERE analyzedAt < :timestamp")
    suspend fun deleteOldAnalyses(timestamp: Long)

    /**
     * すべてのAI分析を削除
     */
    @Query("DELETE FROM article_analysis")
    suspend fun deleteAllAnalyses()

    /**
     * AI分析の総数を取得
     */
    @Query("SELECT COUNT(*) FROM article_analysis")
    suspend fun getAnalysisCount(): Int
}