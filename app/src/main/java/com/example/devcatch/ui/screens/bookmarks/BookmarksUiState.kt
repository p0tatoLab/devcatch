package com.example.devcatch.ui.screens.bookmarks

import com.example.devcatch.domain.model.ArticleWithAnalysis

/**
 * ブックマーク画面のUIState
 */
data class BookmarksUiState(
    val articles: List<ArticleWithAnalysis> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)