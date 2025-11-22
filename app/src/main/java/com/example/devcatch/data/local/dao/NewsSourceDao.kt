package com.example.devcatch.data.local.dao

import androidx.room.*
import com.example.devcatch.data.local.entity.NewsSourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsSourceDao {

    /**
     * すべてのニュースソースを取得
     */
    @Query("SELECT * FROM news_sources ORDER BY priority DESC, name ASC")
    fun getAllSources(): Flow<List<NewsSourceEntity>>

    /**
     * 有効なニュースソースを取得
     */
    @Query("SELECT * FROM news_sources WHERE isEnabled = 1 ORDER BY priority DESC")
    fun getEnabledSources(): Flow<List<NewsSourceEntity>>

    /**
     * カテゴリ別にニュースソースを取得
     */
    @Query("SELECT * FROM news_sources WHERE category = :category AND isEnabled = 1")
    fun getSourcesByCategory(category: String): Flow<List<NewsSourceEntity>>

    /**
     * タイプ別にニュースソースを取得
     */
    @Query("SELECT * FROM news_sources WHERE type = :type AND isEnabled = 1")
    fun getSourcesByType(type: String): Flow<List<NewsSourceEntity>>

    /**
     * IDでニュースソースを取得
     */
    @Query("SELECT * FROM news_sources WHERE id = :sourceId")
    suspend fun getSourceById(sourceId: String): NewsSourceEntity?

    /**
     * ニュースソースを挿入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(source: NewsSourceEntity)

    /**
     * 複数のニュースソースを挿入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<NewsSourceEntity>)

    /**
     * ニュースソースを更新
     */
    @Update
    suspend fun updateSource(source: NewsSourceEntity)

    /**
     * 有効/無効を切り替え
     */
    @Query("UPDATE news_sources SET isEnabled = :isEnabled WHERE id = :sourceId")
    suspend fun updateSourceEnabled(sourceId: String, isEnabled: Boolean)

    /**
     * ニュースソースを削除
     */
    @Delete
    suspend fun deleteSource(source: NewsSourceEntity)

    /**
     * すべてのニュースソースを削除
     */
    @Query("DELETE FROM news_sources")
    suspend fun deleteAllSources()
}