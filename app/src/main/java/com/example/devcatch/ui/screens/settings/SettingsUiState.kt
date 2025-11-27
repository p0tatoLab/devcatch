package com.example.devcatch.ui.screens.settings

import com.example.devcatch.domain.model.NewsSource

/**
 * 設定画面のUIState
 */
data class SettingsUiState(
    val newsSources: List<NewsSource> = emptyList(),
    val articleCount: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)