package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.analyzer.TrendAnalyzer
import com.example.devcatch.domain.model.TrendReport
import com.example.devcatch.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * トレンドレポートを取得するUseCase
 */
class GetTrendReportUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val trendAnalyzer: TrendAnalyzer
) {
    /**
     * トレンドレポートを生成
     */
    suspend operator fun invoke(): TrendReport {
        val articles = articleRepository.getAllArticlesWithAnalysis().first()
        return trendAnalyzer.analyzeTrends(articles)
    }
}