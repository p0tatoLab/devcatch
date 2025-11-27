package com.example.devcatch.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devcatch.domain.repository.ArticleRepository
import com.example.devcatch.domain.repository.NewsSourceRepository
import com.example.devcatch.domain.usecase.DeleteOldArticlesUseCase
import com.example.devcatch.domain.usecase.GetNewsSourcesUseCase
import com.example.devcatch.domain.usecase.ToggleNewsSourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 設定画面のViewModel
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getNewsSourcesUseCase: GetNewsSourcesUseCase,
    private val toggleNewsSourceUseCase: ToggleNewsSourceUseCase,
    private val deleteOldArticlesUseCase: DeleteOldArticlesUseCase,
    private val articleRepository: ArticleRepository,
    private val newsSourceRepository: NewsSourceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(isLoading = true))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    /**
     * 設定を読み込む
     */
    private fun loadSettings() {
        viewModelScope.launch {
            combine(
                getNewsSourcesUseCase(),
                flow { emit(articleRepository.getArticleCount()) }
            ) { sources, count ->
                SettingsUiState(
                    newsSources = sources,
                    articleCount = count,
                    isLoading = false
                )
            }
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message,
                            isLoading = false
                        )
                    }
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    /**
     * ニュースソースの有効/無効を切り替え
     */
    fun toggleSource(sourceId: String) {
        viewModelScope.launch {
            toggleNewsSourceUseCase(sourceId)
        }
    }

    /**
     * 古い記事を削除
     */
    fun deleteOldArticles(daysOld: Int = 30) {
        viewModelScope.launch {
            deleteOldArticlesUseCase(daysOld)
            // 記事数を再取得
            val count = articleRepository.getArticleCount()
            _uiState.update { it.copy(articleCount = count) }
        }
    }

    /**
     * すべての記事を削除
     */
    fun deleteAllArticles() {
        viewModelScope.launch {
            newsSourceRepository.getAllSources().first().forEach { source ->
                // すべてのソースから記事を削除する処理
                // 今回は簡易的に30日以上前の記事を削除
                deleteOldArticlesUseCase(0)
            }
            val count = articleRepository.getArticleCount()
            _uiState.update { it.copy(articleCount = count) }
        }
    }

    /**
     * デフォルトソースにリセット
     */
    fun resetToDefaultSources() {
        viewModelScope.launch {
            newsSourceRepository.resetToDefaultSources()
        }
    }
}