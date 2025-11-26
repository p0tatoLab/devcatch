package com.example.devcatch.data.repository

import com.example.devcatch.data.local.dao.NewsSourceDao
import com.example.devcatch.data.local.entity.toEntity
import com.example.devcatch.data.local.entity.toDomainModel
import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.model.NewsSource
import com.example.devcatch.domain.model.Priority
import com.example.devcatch.domain.model.SourceType
import com.example.devcatch.domain.repository.NewsSourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NewsSourceRepositoryの実装
 */
@Singleton
class NewsSourceRepositoryImpl @Inject constructor(
    private val newsSourceDao: NewsSourceDao
) : NewsSourceRepository {

    override fun getAllSources(): Flow<List<NewsSource>> {
        return newsSourceDao.getAllSources().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getEnabledSources(): Flow<List<NewsSource>> {
        return newsSourceDao.getEnabledSources().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getSourcesByCategory(category: Category): Flow<List<NewsSource>> {
        return newsSourceDao.getSourcesByCategory(category.name).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getSourcesByType(type: SourceType): Flow<List<NewsSource>> {
        return newsSourceDao.getSourcesByType(type.name).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getSourcesByPriority(priority: Priority): Flow<List<NewsSource>> {
        return newsSourceDao.getAllSources().map { entities ->
            entities
                .map { it.toDomainModel() }
                .filter { it.priority == priority }
        }
    }

    override suspend fun getSourceById(sourceId: String): NewsSource? {
        return newsSourceDao.getSourceById(sourceId)?.toDomainModel()
    }

    override suspend fun insertSource(source: NewsSource) {
        newsSourceDao.insertSource(source.toEntity())
    }

    override suspend fun insertSources(sources: List<NewsSource>) {
        newsSourceDao.insertSources(sources.map { it.toEntity() })
    }

    override suspend fun updateSource(source: NewsSource) {
        newsSourceDao.updateSource(source.toEntity())
    }

    override suspend fun toggleSourceEnabled(sourceId: String) {
        val source = getSourceById(sourceId)
        if (source != null) {
            newsSourceDao.updateSourceEnabled(sourceId, !source.isEnabled)
        }
    }

    override suspend fun deleteSource(source: NewsSource) {
        newsSourceDao.deleteSource(source.toEntity())
    }

    override suspend fun initializeDefaultSources() {
        try {
            // Flowから最初の値を取得
            val existingSources = newsSourceDao.getAllSources().first()

            if (existingSources.isEmpty()) {
                val defaultSources = DefaultNewsSources.getDefaultSources()
                insertSources(defaultSources)
            }
        } catch (e: Exception) {
            android.util.Log.e("NewsSourceRepository", "Error initializing sources", e)
        }
    }

    override suspend fun resetToDefaultSources() {
        // すべてのソースを削除してデフォルトを再挿入
        newsSourceDao.deleteAllSources()
        val defaultSources = DefaultNewsSources.getDefaultSources()
        insertSources(defaultSources)
    }
}