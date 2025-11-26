package com.example.devcatch.ui.screens.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devcatch.domain.usecase.BookmarkArticleUseCase
import com.example.devcatch.domain.usecase.GetArticlesUseCase
import com.example.devcatch.domain.usecase.MarkAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ブックマーク画面のViewModel
 */
@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookmarksUiState(isLoading = true))
    val uiState: StateFlow<BookmarksUiState> = _uiState.asStateFlow()

    init {
        loadBookmarkedArticles()
    }

    /**
     * ブックマークされた記事を読み込む
     */
    private fun loadBookmarkedArticles() {
        viewModelScope.launch {
            getArticlesUseCase.bookmarkedWithAnalysis()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message,
                            isLoading = false
                        )
                    }
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
     * ブックマークを解除
     */
    fun removeBookmark(articleId: String) {
        viewModelScope.launch {
            bookmarkArticleUseCase.remove(articleId)
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