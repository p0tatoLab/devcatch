package com.example.devcatch.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devcatch.domain.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * AI分析結果のEntity
 */
@Entity(tableName = "article_analysis")
data class ArticleAnalysisEntity(
    @PrimaryKey
    val articleId: String,

    // 要約
    val oneLinerSummary: String,
    val detailedSummary: String,
    val keyPoints: String, // JSON配列として保存

    // 読むべきか判定
    val shouldReadVerdict: Boolean,
    val shouldReadReason: String,

    // メタ情報
    val difficulty: String,
    val estimatedReadingTime: Int,
    val practicalityLevel: String,
    val timelineRelevance: String,
    val learningPriority: Int,

    // 関連情報
    val relatedTechnologies: String, // JSON配列として保存
    val tags: String, // JSON配列として保存

    // スコア
    val relevanceScore: Int,

    // アクション
    val actionItems: String, // JSON配列として保存

    // 警告・前提知識
    val deprecationWarning: String?,
    val prerequisites: String, // JSON配列として保存
    val officialResource: String?,

    // 分析日時
    val analyzedAt: Long = System.currentTimeMillis()
)

/**
 * ArticleAnalysisEntity → ArticleAnalysis への変換
 */
fun ArticleAnalysisEntity.toDomainModel(): ArticleAnalysis {
    val gson = Gson()
    val stringListType = object : TypeToken<List<String>>() {}.type

    return ArticleAnalysis(
        articleId = articleId,
        oneLinerSummary = oneLinerSummary,
        detailedSummary = detailedSummary,
        keyPoints = gson.fromJson(keyPoints, stringListType),
        shouldRead = ShouldRead(
            verdict = shouldReadVerdict,
            reason = shouldReadReason
        ),
        difficulty = Difficulty.fromString(difficulty),
        estimatedReadingTime = estimatedReadingTime,
        practicalityLevel = PracticalityLevel.fromString(practicalityLevel),
        timelineRelevance = TimelineRelevance.fromString(timelineRelevance),
        learningPriority = learningPriority,
        relatedTechnologies = gson.fromJson(relatedTechnologies, stringListType),
        tags = gson.fromJson(tags, stringListType),
        relevanceScore = relevanceScore,
        actionItems = gson.fromJson(actionItems, stringListType),
        deprecationWarning = deprecationWarning,
        prerequisites = gson.fromJson(prerequisites, stringListType),
        officialResource = officialResource,
        analyzedAt = analyzedAt
    )
}

/**
 * ArticleAnalysis → ArticleAnalysisEntity への変換
 */
fun ArticleAnalysis.toEntity(): ArticleAnalysisEntity {
    val gson = Gson()

    return ArticleAnalysisEntity(
        articleId = articleId,
        oneLinerSummary = oneLinerSummary,
        detailedSummary = detailedSummary,
        keyPoints = gson.toJson(keyPoints),
        shouldReadVerdict = shouldRead.verdict,
        shouldReadReason = shouldRead.reason,
        difficulty = difficulty.name,
        estimatedReadingTime = estimatedReadingTime,
        practicalityLevel = practicalityLevel.name,
        timelineRelevance = timelineRelevance.name,
        learningPriority = learningPriority,
        relatedTechnologies = gson.toJson(relatedTechnologies),
        tags = gson.toJson(tags),
        relevanceScore = relevanceScore,
        actionItems = gson.toJson(actionItems),
        deprecationWarning = deprecationWarning,
        prerequisites = gson.toJson(prerequisites),
        officialResource = officialResource,
        analyzedAt = analyzedAt
    )
}