package com.example.devcatch.domain.analyzer

import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.model.TrendReport
import com.example.devcatch.domain.model.TopicTrend
import com.example.devcatch.domain.model.Trend
import com.example.devcatch.domain.model.EmergingLibrary
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * トレンド分析
 */
@Singleton
class TrendAnalyzer @Inject constructor() {

    /**
     * 記事からトレンドレポートを生成
     */
    fun analyzeTrends(articles: List<ArticleWithAnalysis>): TrendReport {
        // 過去7日間の記事を取得
        val weekAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        val recentArticles = articles.filter { it.publishedAt >= weekAgo }

        // トップトピックを抽出
        val topTopics = extractTopTopics(recentArticles)

        // 注目ライブラリを抽出
        val emergingLibraries = extractEmergingLibraries(recentArticles)

        // 週次サマリーを生成
        val weekSummary = generateWeekSummary(recentArticles, topTopics)

        return TrendReport(
            topTopics = topTopics,
            emergingLibraries = emergingLibraries,
            deprecations = emptyList(), // 今回は実装しない
            mustLearn = emptyList(), // 今回は実装しない
            weekSummary = weekSummary,
            nextWeekPrediction = "今週のトレンドが継続すると予想されます",
            generatedAt = System.currentTimeMillis()
        )
    }

    /**
     * トップトピックを抽出
     */
    private fun extractTopTopics(articles: List<ArticleWithAnalysis>): List<TopicTrend> {
        // カテゴリごとの記事数をカウント
        val categoryCount = articles.groupingBy { it.category }.eachCount()

        // トップ5のカテゴリを抽出
        return categoryCount.entries
            .sortedByDescending { it.value }
            .take(5)
            .map { (category, count) ->
                TopicTrend(
                    topic = category.displayName,
                    count = count,
                    trend = determineTrend(category, articles),
                    importance = (count * 10 / articles.size).coerceIn(1, 10),
                    summary = "${category.displayName}に関する記事が${count}件投稿されました"
                )
            }
    }

    /**
     * トレンドを判定
     */
    private fun determineTrend(category: Category, articles: List<ArticleWithAnalysis>): Trend {
        // 過去3日間と4-7日前の記事数を比較
        val threeDaysAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)
        val sevenDaysAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)

        val recentCount = articles.count {
            it.category == category && it.publishedAt >= threeDaysAgo
        }
        val previousCount = articles.count {
            it.category == category && it.publishedAt >= sevenDaysAgo && it.publishedAt < threeDaysAgo
        }

        return when {
            recentCount > previousCount * 1.5 -> Trend.RISING
            recentCount < previousCount * 0.5 -> Trend.DECLINING
            else -> Trend.STABLE
        }
    }

    /**
     * 注目ライブラリを抽出
     */
    private fun extractEmergingLibraries(articles: List<ArticleWithAnalysis>): List<EmergingLibrary> {
        // AI分析から関連技術を抽出
        val technologies = articles
            .mapNotNull { it.analysis?.relatedTechnologies }
            .flatten()
            .groupingBy { it }
            .eachCount()

        // 出現回数が多い順にソート
        return technologies.entries
            .sortedByDescending { it.value }
            .take(5)
            .map { (tech, count) ->
                EmergingLibrary(
                    name = tech,
                    purpose = "最近${count}件の記事で言及されています",
                    githubUrl = null,
                    whyMatters = "注目度が高まっている技術です"
                )
            }
    }

    /**
     * 週次サマリーを生成
     */
    private fun generateWeekSummary(
        articles: List<ArticleWithAnalysis>,
        topTopics: List<TopicTrend>
    ): String {
        val totalArticles = articles.size
        val topTopic = topTopics.firstOrNull()?.topic ?: "記事"

        return "今週は${totalArticles}件の記事が投稿されました。" +
                "特に${topTopic}に関する話題が注目を集めています。"
    }
}