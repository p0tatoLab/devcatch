package com.example.devcatch.ui.screens.search

import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Category

/**
 * 検索画面のUIState
 */
data class SearchUiState(
    val query: String = "",
    val articles: List<ArticleWithAnalysis> = emptyList(),
    val selectedCategory: Category? = null,
    val isSearching: Boolean = false,
    val hasSearched: Boolean = false,
    val error: String? = null
)