package com.example.devcatch.data.initializer

import com.example.devcatch.data.local.dao.ArticleAnalysisDao
import com.example.devcatch.data.local.dao.ArticleDao
import com.example.devcatch.data.local.entity.toEntity
import com.example.devcatch.data.repository.TestDataGenerator
import com.example.devcatch.domain.repository.NewsSourceRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * アプリ起動時のデータ初期化
 */
@Singleton
class DataInitializer @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository,
    private val articleDao: ArticleDao,
    private val articleAnalysisDao: ArticleAnalysisDao
) {

    /**
     * 初期データをセットアップ
     */
    suspend fun initialize() {
        // デフォルトのニュースソースを初期化
        newsSourceRepository.initializeDefaultSources()

        // テストデータを初期化（開発中のみ）
        initializeTestData()
    }

    /**
     * テストデータを初期化
     */
    private suspend fun initializeTestData() {
        // 既存の記事数を確認
        val articleCount = articleDao.getArticleCount()

        if (articleCount == 0) {
            // テスト記事を挿入
            val testArticles = TestDataGenerator.generateTestArticles()
            articleDao.insertArticles(testArticles.map { it.toEntity() })

            // テストAI分析を挿入
            val testAnalyses = TestDataGenerator.generateTestAnalysis()
            articleAnalysisDao.insertAnalyses(testAnalyses.map { it.toEntity() })
        }
    }
}