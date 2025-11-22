package com.example.devcatch.util

/**
 * アプリ全体で使用する定数
 */
object Constants {

    // データベース
    const val DATABASE_NAME = "devcatch_database"
    const val DATABASE_VERSION = 1

    // キャッシュ
    const val CACHE_VALIDITY_HOURS = 24L
    const val IMAGE_CACHE_SIZE_MB = 100L

    // ページング
    const val PAGE_SIZE = 20
    const val INITIAL_LOAD_SIZE = 40

    // AI分析
    const val AI_ANALYSIS_BATCH_SIZE = 10
    const val AI_ANALYSIS_DAILY_LIMIT = 50

    // WorkManager
    const val WORK_TAG_FETCH_ARTICLES = "fetch_articles"
    const val WORK_TAG_ANALYZE_ARTICLES = "analyze_articles"
    const val WORK_TAG_CLEANUP = "cleanup_old_data"

    // 通知
    const val NOTIFICATION_CHANNEL_ID = "devcatch_news"
    const val NOTIFICATION_CHANNEL_NAME = "新着記事"

    // SharedPreferences
    const val PREF_NAME = "devcatch_preferences"
    const val PREF_LAST_SYNC = "last_sync_timestamp"
    const val PREF_NOTIFICATION_ENABLED = "notification_enabled"
    const val PREF_MIN_IMPORTANCE_SCORE = "min_importance_score"
}