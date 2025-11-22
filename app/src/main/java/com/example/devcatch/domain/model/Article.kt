package com.example.devcatch.domain.model

/**
 * UI層で使用する記事モデル
 */
data class Article(
    val id: String,
    val title: String,
    val url: String,
    val source: String,
    val sourceFavicon: String?,
    val category: Category,
    val publishedAt: Long, // Unix timestamp
    val content: String,
    val summary: String?,
    val imageUrl: String?,
    val isBookmarked: Boolean = false,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)