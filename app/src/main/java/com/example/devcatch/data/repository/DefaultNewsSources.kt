package com.example.devcatch.data.repository

import com.example.devcatch.domain.model.*

/**
 * デフォルトのニュースソース定義
 */
object DefaultNewsSources {

    fun getDefaultSources(): List<NewsSource> {
        return listOf(
            // ========== Google公式 ==========
            NewsSource(
                id = "android_developers_blog",
                name = "Android Developers Blog",
                url = "https://android-developers.googleblog.com/feeds/posts/default",
                type = SourceType.RSS,
                category = Category.GOOGLE_NEWS,
                priority = Priority.HIGH,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            NewsSource(
                id = "android_developers_youtube",
                name = "Android Developers YouTube",
                url = "https://www.youtube.com/feeds/videos.xml?channel_id=UCVHFbqXqoYvEWM1Ddxl0QDg",
                type = SourceType.RSS,
                category = Category.GOOGLE_NEWS,
                priority = Priority.HIGH,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            // ========== Kotlin公式 ==========
            NewsSource(
                id = "kotlin_blog",
                name = "Kotlin Blog",
                url = "https://blog.jetbrains.com/kotlin/feed/",
                type = SourceType.RSS,
                category = Category.KOTLIN,
                priority = Priority.HIGH,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            // ========== Jetpack Compose ==========
            NewsSource(
                id = "compose_samples",
                name = "Compose Samples (GitHub)",
                url = "https://github.com/android/compose-samples",
                type = SourceType.API,
                category = Category.JETPACK_COMPOSE,
                priority = Priority.HIGH,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            // ========== コミュニティ ==========
            NewsSource(
                id = "medium_android",
                name = "Medium - Android",
                url = "https://medium.com/feed/tag/android",
                type = SourceType.RSS,
                category = Category.COMMUNITY,
                priority = Priority.MEDIUM,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            NewsSource(
                id = "dev_to_android",
                name = "DEV.to - Android",
                url = "https://dev.to/feed/tag/android",
                type = SourceType.RSS,
                category = Category.COMMUNITY,
                priority = Priority.MEDIUM,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            // ========== Android Weekly ==========
            NewsSource(
                id = "android_weekly",
                name = "Android Weekly",
                url = "https://androidweekly.net/",
                type = SourceType.SCRAPING,
                category = Category.COMMUNITY,
                priority = Priority.HIGH,
                updateFrequency = UpdateFrequency.WEEKLY,
                isEnabled = true
            ),

            // ========== プロダンドロイド（日本語） ==========
            NewsSource(
                id = "prodandroid_jp",
                name = "プロダンドロイド",
                url = "https://prodandroid.com/feed/",
                type = SourceType.RSS,
                category = Category.COMMUNITY,
                priority = Priority.MEDIUM,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            // ========== GitHub Trending ==========
            NewsSource(
                id = "github_trending_kotlin",
                name = "GitHub Trending - Kotlin",
                url = "https://github.com/trending/kotlin?since=daily",
                type = SourceType.SCRAPING,
                category = Category.KOTLIN,
                priority = Priority.MEDIUM,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            NewsSource(
                id = "github_trending_android",
                name = "GitHub Trending - Android",
                url = "https://github.com/trending/java?spoken_language_code=&since=daily",
                type = SourceType.SCRAPING,
                category = Category.LIBRARIES,
                priority = Priority.MEDIUM,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = true
            ),

            // ========== Android Police ==========
            NewsSource(
                id = "android_police",
                name = "Android Police",
                url = "https://www.androidpolice.com/feed/",
                type = SourceType.RSS,
                category = Category.ANDROID_OS,
                priority = Priority.LOW,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = false // デフォルトでオフ
            ),

            // ========== XDA Developers ==========
            NewsSource(
                id = "xda_developers",
                name = "XDA Developers",
                url = "https://www.xda-developers.com/feed/",
                type = SourceType.RSS,
                category = Category.COMMUNITY,
                priority = Priority.LOW,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = false // デフォルトでオフ
            ),

            // ========== Stack Overflow Blog ==========
            NewsSource(
                id = "stackoverflow_blog",
                name = "Stack Overflow Blog",
                url = "https://stackoverflow.blog/feed/",
                type = SourceType.RSS,
                category = Category.OTHER,
                priority = Priority.LOW,
                updateFrequency = UpdateFrequency.WEEKLY,
                isEnabled = false // デフォルトでオフ
            ),

            // ========== Material Design ==========
            NewsSource(
                id = "material_design_blog",
                name = "Material Design Blog",
                url = "https://material.io/blog/feed",
                type = SourceType.RSS,
                category = Category.MATERIAL_DESIGN,
                priority = Priority.MEDIUM,
                updateFrequency = UpdateFrequency.WEEKLY,
                isEnabled = true
            ),

            // ========== Android Authority ==========
            NewsSource(
                id = "android_authority",
                name = "Android Authority",
                url = "https://www.androidauthority.com/feed/",
                type = SourceType.RSS,
                category = Category.ANDROID_OS,
                priority = Priority.LOW,
                updateFrequency = UpdateFrequency.DAILY,
                isEnabled = false // デフォルトでオフ
            )
        )
    }

    /**
     * デフォルトで有効なソースのみ取得
     */
    fun getEnabledDefaultSources(): List<NewsSource> {
        return getDefaultSources().filter { it.isEnabled }
    }

    /**
     * カテゴリ別にソースを取得
     */
    fun getSourcesByCategory(category: Category): List<NewsSource> {
        return getDefaultSources().filter { it.category == category }
    }

    /**
     * 優先度別にソースを取得
     */
    fun getSourcesByPriority(priority: Priority): List<NewsSource> {
        return getDefaultSources().filter { it.priority == priority }
    }
}