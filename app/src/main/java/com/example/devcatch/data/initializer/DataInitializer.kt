package com.example.devcatch.data.initializer

import com.example.devcatch.domain.repository.NewsSourceRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * アプリ起動時のデータ初期化
 */
@Singleton
class DataInitializer @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository
) {

    /**
     * 初期データをセットアップ
     */
    suspend fun initialize() {
        // デフォルトのニュースソースを初期化
        newsSourceRepository.initializeDefaultSources()
    }
}