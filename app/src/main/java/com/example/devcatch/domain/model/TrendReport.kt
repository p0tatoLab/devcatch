package com.example.devcatch.domain.model

/**
 * トレンドレポート
 */
data class TrendReport(
    val topTopics: List<TopicTrend>,
    val emergingLibraries: List<EmergingLibrary>,
    val deprecations: List<Deprecation>,
    val mustLearn: List<MustLearnSkill>,
    val weekSummary: String,
    val nextWeekPrediction: String,
    val generatedAt: Long = System.currentTimeMillis()
)

/**
 * トップトピック
 */
data class TopicTrend(
    val topic: String,
    val count: Int,
    val trend: Trend,
    val importance: Int, // 1-10
    val summary: String
)

/**
 * トレンド方向
 */
enum class Trend {
    RISING,     // 上昇中
    STABLE,     // 安定
    DECLINING   // 下降中
}

/**
 * 注目ライブラリ
 */
data class EmergingLibrary(
    val name: String,
    val purpose: String,
    val githubUrl: String?,
    val whyMatters: String
)

/**
 * 非推奨技術
 */
data class Deprecation(
    val technology: String,
    val alternative: String,
    val timeline: String
)

/**
 * 学ぶべきスキル
 */
data class MustLearnSkill(
    val skill: String,
    val reason: String,
    val resources: List<String>
)