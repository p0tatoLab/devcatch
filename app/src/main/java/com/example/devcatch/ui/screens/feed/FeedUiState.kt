package com.example.devcatch.ui.screens.feed

import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Category

/**
 * フィード画面のUIState
 */
data class FeedUiState(
    val articles: List<ArticleWithAnalysis> = emptyList(),
    val selectedCategory: Category? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val unreadCount: Int = 0
)