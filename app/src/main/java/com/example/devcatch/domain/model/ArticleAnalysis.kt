package com.example.devcatch.domain.model

/**
 * AIによる記事分析結果
 */
data class ArticleAnalysis(
    val articleId: String,

    // 要約
    val oneLinerSummary: String,          // 1行要約（30文字以内）
    val detailedSummary: String,          // 詳細要約（3-5文）
    val keyPoints: List<String>,          // 重要ポイント

    // 読むべきか判定
    val shouldRead: ShouldRead,

    // メタ情報
    val difficulty: Difficulty,           // 難易度
    val estimatedReadingTime: Int,        // 読了時間（分）
    val practicalityLevel: PracticalityLevel, // 実践度
    val timelineRelevance: TimelineRelevance, // 時間軸
    val learningPriority: Int,            // 学習優先度（1-10）

    // 関連情報
    val relatedTechnologies: List<String>, // 関連技術
    val tags: List<String>,                // タグ

    // スコア
    val relevanceScore: Int,              // 重要度スコア（1-10）

    // アクション
    val actionItems: List<String>,        // すぐ試せること

    // 警告・前提知識
    val deprecationWarning: String?,      // 非推奨警告
    val prerequisites: List<String>,      // 前提知識
    val officialResource: String?,        // 公式リソースURL

    // 分析日時
    val analyzedAt: Long = System.currentTimeMillis()
)

/**
 * 読むべきか判定
 */
data class ShouldRead(
    val verdict: Boolean,
    val reason: String
)

/**
 * 難易度
 */
enum class Difficulty(val displayName: String) {
    BEGINNER("初級"),
    INTERMEDIATE("中級"),
    ADVANCED("上級");

    companion object {
        fun fromString(value: String): Difficulty {
            return when (value.lowercase()) {
                "beginner" -> BEGINNER
                "intermediate" -> INTERMEDIATE
                "advanced" -> ADVANCED
                else -> INTERMEDIATE
            }
        }
    }
}

/**
 * 実践度
 */
enum class PracticalityLevel(val displayName: String) {
    PRACTICAL("実践的"),
    THEORETICAL("理論的");

    companion object {
        fun fromString(value: String): PracticalityLevel {
            return when (value.lowercase()) {
                "practical" -> PRACTICAL
                "theoretical" -> THEORETICAL
                else -> PRACTICAL
            }
        }
    }
}

/**
 * 時間軸（いつ使えるか）
 */
enum class TimelineRelevance(val displayName: String) {
    IMMEDIATE("すぐ使える"),
    FUTURE("将来のため");

    companion object {
        fun fromString(value: String): TimelineRelevance {
            return when (value.lowercase()) {
                "immediate" -> IMMEDIATE
                "future" -> FUTURE
                else -> IMMEDIATE
            }
        }
    }
}