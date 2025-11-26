package com.example.devcatch.ui.screens.detail

import com.example.devcatch.domain.model.ArticleWithAnalysis

/**
 * 記事詳細画面のUIState
 */
data class ArticleDetailUiState(
    val article: ArticleWithAnalysis? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)