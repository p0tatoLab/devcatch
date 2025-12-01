package com.example.devcatch.ui.screens.trends

import com.example.devcatch.domain.model.TrendReport

/**
 * トレンド画面のUIState
 */
data class TrendsUiState(
    val trendReport: TrendReport? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)