package com.example.devcatch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devcatch.domain.model.*

/**
 * ニュースソースのEntity
 */
@Entity(tableName = "news_sources")
data class NewsSourceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String,
    val type: String,
    val category: String,
    val priority: Int,
    val updateFrequency: String,
    val isEnabled: Boolean = true
)

/**
 * NewsSourceEntity → NewsSource への変換
 */
fun NewsSourceEntity.toDomainModel(): NewsSource {
    return NewsSource(
        id = id,
        name = name,
        url = url,
        type = SourceType.valueOf(type),
        category = Category.valueOf(category),
        priority = Priority.fromInt(priority),
        updateFrequency = UpdateFrequency.fromString(updateFrequency),
        isEnabled = isEnabled
    )
}

/**
 * NewsSource → NewsSourceEntity への変換
 */
fun NewsSource.toEntity(): NewsSourceEntity {
    return NewsSourceEntity(
        id = id,
        name = name,
        url = url,
        type = type.name,
        category = category.name,
        priority = priority.value,
        updateFrequency = updateFrequency.name,
        isEnabled = isEnabled
    )
}