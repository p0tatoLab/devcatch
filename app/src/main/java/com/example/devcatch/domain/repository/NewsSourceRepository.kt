package com.example.devcatch.domain.repository

import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.model.NewsSource
import com.example.devcatch.domain.model.Priority
import com.example.devcatch.domain.model.SourceType
import kotlinx.coroutines.flow.Flow

/**
 * ニュースソースのRepository
 */
interface NewsSourceRepository {

    /**
     * すべてのソースを取得
     */
    fun getAllSources(): Flow<List<NewsSource>>

    /**
     * 有効なソースを取得
     */
    fun getEnabledSources(): Flow<List<NewsSource>>

    /**
     * カテゴリ別にソースを取得
     */
    fun getSourcesByCategory(category: Category): Flow<List<NewsSource>>

    /**
     * タイプ別にソースを取得
     */
    fun getSourcesByType(type: SourceType): Flow<List<NewsSource>>

    /**
     * 優先度別にソースを取得
     */
    fun getSourcesByPriority(priority: Priority): Flow<List<NewsSource>>

    /**
     * IDでソースを取得
     */
    suspend fun getSourceById(sourceId: String): NewsSource?

    /**
     * ソースを追加
     */
    suspend fun insertSource(source: NewsSource)

    /**
     * 複数のソースを追加
     */
    suspend fun insertSources(sources: List<NewsSource>)

    /**
     * ソースを更新
     */
    suspend fun updateSource(source: NewsSource)

    /**
     * ソースの有効/無効を切り替え
     */
    suspend fun toggleSourceEnabled(sourceId: String)

    /**
     * ソースを削除
     */
    suspend fun deleteSource(source: NewsSource)

    /**
     * デフォルトソースを初期化
     */
    suspend fun initializeDefaultSources()

    /**
     * デフォルトソースをリセット
     */
    suspend fun resetToDefaultSources()
}