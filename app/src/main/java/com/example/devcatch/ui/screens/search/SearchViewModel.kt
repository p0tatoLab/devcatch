package com.example.devcatch.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.usecase.BookmarkArticleUseCase
import com.example.devcatch.domain.usecase.MarkAsReadUseCase
import com.example.devcatch.domain.usecase.SearchArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 検索画面のViewModel
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    /**
     * 検索クエリを更新
     */
    fun updateQuery(query: String) {
        _uiState.update { it.copy(query = query) }

        // デバウンス検索（500ms待ってから検索実行）
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (query.isNotBlank()) {
                search()
            } else {
                _uiState.update {
                    it.copy(
                        articles = emptyList(),
                        hasSearched = false
                    )
                }
            }
        }
    }

    /**
     * 検索を実行
     */
    private fun search() {
        val query = _uiState.value.query
        val selectedCategory = _uiState.value.selectedCategory

        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSearching = true) }

            // withAnalysis()を使用
            searchArticlesUseCase.withAnalysis(query)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message,
                            isSearching = false,
                            hasSearched = true
                        )
                    }
                }
                .collect { articles ->
                    // カテゴリフィルターを適用
                    val filteredArticles = if (selectedCategory != null) {
                        articles.filter { it.category == selectedCategory }
                    } else {
                        articles
                    }

                    _uiState.update {
                        it.copy(
                            articles = filteredArticles,
                            isSearching = false,
                            hasSearched = true,
                            error = null
                        )
                    }
                }
        }
    }

    /**
     * カテゴリフィルターを選択
     */
    fun selectCategory(category: Category?) {
        _uiState.update { it.copy(selectedCategory = category) }

        // カテゴリ変更時に再検索
        if (_uiState.value.hasSearched) {
            search()
        }
    }

    /**
     * ブックマークを切り替え
     */
    fun toggleBookmark(articleId: String) {
        viewModelScope.launch {
            bookmarkArticleUseCase.toggle(articleId)
        }
    }

    /**
     * 記事を既読にする
     */
    fun markAsRead(articleId: String) {
        viewModelScope.launch {
            markAsReadUseCase.markAsRead(articleId)
        }
    }

    /**
     * 検索をクリア
     */
    fun clearSearch() {
        _uiState.update {
            SearchUiState()
        }
        searchJob?.cancel()
    }
}