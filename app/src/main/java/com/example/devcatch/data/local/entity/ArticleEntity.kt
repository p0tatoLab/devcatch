package com.example.devcatch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devcatch.domain.model.Article
import com.example.devcatch.domain.model.Category

/**
 * 記事のEntity（データベーステーブル）
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val url: String,
    val source: String,
    val sourceFavicon: String?,
    val category: String, // Category enumを文字列で保存
    val publishedAt: Long,
    val content: String,
    val summary: String?,
    val imageUrl: String?,
    val isBookmarked: Boolean = false,
    val isRead: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * ArticleEntity → Article への変換
 */
fun ArticleEntity.toDomainModel(): Article {
    return Article(
        id = id,
        title = title,
        url = url,
        source = source,
        sourceFavicon = sourceFavicon,
        category = Category.valueOf(category),
        publishedAt = publishedAt,
        content = content,
        summary = summary,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked,
        isRead = isRead,
        createdAt = createdAt
    )
}

/**
 * Article → ArticleEntity への変換
 */
fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        id = id,
        title = title,
        url = url,
        source = source,
        sourceFavicon = sourceFavicon,
        category = category.name,
        publishedAt = publishedAt,
        content = content,
        summary = summary,
        imageUrl = imageUrl,
        isBookmarked = isBookmarked,
        isRead = isRead,
        createdAt = createdAt
    )
}