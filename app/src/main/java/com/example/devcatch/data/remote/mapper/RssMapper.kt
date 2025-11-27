package com.example.devcatch.data.remote.mapper

import com.example.devcatch.data.remote.dto.RssItemDto
import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.Category
import com.example.devcatch.util.TimeUtils.toUnixTimestamp
import java.util.UUID

/**
 * RSSアイテムをArticleに変換
 */
object RssMapper {

    /**
     * RssItemDto → Article
     */
    fun toArticle(
        dto: RssItemDto,
        sourceName: String,
        defaultCategory: Category
    ): Article {
        return Article(
            id = generateArticleId(dto.link),
            title = dto.title.trim(),
            url = dto.link,
            source = sourceName,
            sourceFavicon = null,
            category = inferCategory(dto, defaultCategory),
            publishedAt = parsePublishDate(dto.pubDate),
            content = cleanContent(dto.description ?: ""),
            summary = generateSummary(dto.description ?: ""),
            imageUrl = null,
            isBookmarked = false,
            isRead = false,
            createdAt = System.currentTimeMillis()
        )
    }

    /**
     * URLからユニークなIDを生成
     */
    private fun generateArticleId(url: String): String {
        return UUID.nameUUIDFromBytes(url.toByteArray()).toString()
    }

    /**
     * カテゴリを推測
     */
    private fun inferCategory(dto: RssItemDto, defaultCategory: Category): Category {
        val text = "${dto.title} ${dto.description ?: ""} ${dto.categories.joinToString(" ")}"
            .lowercase()

        return when {
            text.contains("compose") -> Category.JETPACK_COMPOSE
            text.contains("kotlin") && !text.contains("multiplatform") -> Category.KOTLIN
            text.contains("multiplatform") || text.contains("kmp") -> Category.KMP
            text.contains("material") && text.contains("design") -> Category.MATERIAL_DESIGN
            text.contains("android") && (text.contains("ui") || text.contains("view")) -> Category.ANDROID_UI
            text.contains("architecture") -> Category.ARCHITECTURE
            text.contains("performance") -> Category.PERFORMANCE
            text.contains("security") -> Category.SECURITY
            text.contains("test") -> Category.TESTING
            text.contains("ci/cd") || text.contains("github actions") -> Category.CI_CD
            text.contains("library") || text.contains("libraries") -> Category.LIBRARIES
            text.contains("tool") -> Category.TOOLS
            else -> defaultCategory
        }
    }

    /**
     * 公開日をパース
     */
    private fun parsePublishDate(pubDate: String?): Long {
        if (pubDate.isNullOrBlank()) {
            return System.currentTimeMillis()
        }

        return try {
            pubDate.toUnixTimestamp()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }

    /**
     * コンテンツをクリーンアップ（HTMLタグ除去）
     */
    private fun cleanContent(content: String): String {
        return content
            .replace(Regex("<[^>]*>"), "") // HTMLタグ除去
            .replace(Regex("&[a-z]+;"), " ") // HTMLエンティティ除去
            .replace(Regex("\\s+"), " ") // 連続した空白を1つに
            .trim()
    }

    /**
     * 簡易要約を生成（最初の200文字）
     */
    private fun generateSummary(content: String): String? {
        val cleaned = cleanContent(content)
        return if (cleaned.length > 200) {
            cleaned.take(200) + "..."
        } else {
            cleaned.ifBlank { null }
        }
    }
}