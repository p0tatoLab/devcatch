package com.example.devcatch.ui.screens.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devcatch.domain.model.Category
import com.example.devcatch.domain.usecase.BookmarkArticleUseCase
import com.example.devcatch.domain.usecase.GetArticlesUseCase
import com.example.devcatch.domain.usecase.GetUnreadCountUseCase
import com.example.devcatch.domain.usecase.MarkAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * フィード画面のViewModel
 */
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase,
    private val getUnreadCountUseCase: GetUnreadCountUseCase
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<Category?>(null)

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
        loadUnreadCount()
    }

    /**
     * 記事を読み込む
     */
    private fun loadArticles() {
        viewModelScope.launch {
            _selectedCategory
                .flatMapLatest { category ->
                    if (category == null) {
                        getArticlesUseCase.withAnalysis()
                    } else {
                        getArticlesUseCase.byCategoryWithAnalysis(category)
                    }
                }
                .catch { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
                .collect { articles ->
                    _uiState.update {
                        it.copy(
                            articles = articles,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    /**
     * 未読数を読み込む
     */
    private fun loadUnreadCount() {
        viewModelScope.launch {
            getUnreadCountUseCase()
                .catch { e ->
                    // エラーは無視（未読数は補助情報のため）
                }
                .collect { count ->
                    _uiState.update { it.copy(unreadCount = count) }
                }
        }
    }

    /**
     * カテゴリを選択
     */
    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
        _uiState.update { it.copy(selectedCategory = category, isLoading = true) }
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
}