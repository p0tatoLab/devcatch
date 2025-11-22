package com.example.devcatch.domain.model

/**
 * ニュースソース
 */
data class NewsSource(
    val id: String,
    val name: String,
    val url: String,
    val type: SourceType,
    val category: Category,
    val priority: Priority,
    val updateFrequency: UpdateFrequency,
    val isEnabled: Boolean = true
)

/**
 * ソースの種類
 */
enum class SourceType {
    RSS,        // RSSフィード
    API,        // REST API
    SCRAPING    // Webスクレイピング
}

/**
 * 優先度
 */
enum class Priority(val value: Int) {
    HIGH(3),
    MEDIUM(2),
    LOW(1);

    companion object {
        fun fromInt(value: Int): Priority {
            return when (value) {
                3 -> HIGH
                2 -> MEDIUM
                1 -> LOW
                else -> MEDIUM
            }
        }
    }
}

/**
 * 更新頻度
 */
enum class UpdateFrequency(val displayName: String) {
    HOURLY("1時間ごと"),
    DAILY("1日ごと"),
    WEEKLY("1週間ごと");

    companion object {
        fun fromString(value: String): UpdateFrequency {
            return when (value.uppercase()) {
                "HOURLY" -> HOURLY
                "DAILY" -> DAILY
                "WEEKLY" -> WEEKLY
                else -> DAILY
            }
        }
    }
}