package com.example.devcatch.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devcatch.domain.usecase.BookmarkArticleUseCase
import com.example.devcatch.domain.usecase.GetArticleByIdUseCase
import com.example.devcatch.domain.usecase.MarkAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 記事詳細画面のViewModel
 */
@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val getArticleByIdUseCase: GetArticleByIdUseCase,
    private val bookmarkArticleUseCase: BookmarkArticleUseCase,
    private val markAsReadUseCase: MarkAsReadUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleId: String = checkNotNull(savedStateHandle["articleId"])

    private val _uiState = MutableStateFlow(ArticleDetailUiState(isLoading = true))
    val uiState: StateFlow<ArticleDetailUiState> = _uiState.asStateFlow()

    init {
        loadArticle()
        markArticleAsRead()
    }

    /**
     * 記事を読み込む
     */
    private fun loadArticle() {
        viewModelScope.launch {
            getArticleByIdUseCase.withAnalysisAsFlow(articleId)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message ?: "記事の読み込みに失敗しました",
                            isLoading = false
                        )
                    }
                }
                .collect { article ->
                    _uiState.update {
                        it.copy(
                            article = article,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    /**
     * 記事を既読にする
     */
    private fun markArticleAsRead() {
        viewModelScope.launch {
            markAsReadUseCase.markAsRead(articleId)
        }
    }

    /**
     * ブックマークを切り替え
     */
    fun toggleBookmark() {
        viewModelScope.launch {
            bookmarkArticleUseCase.toggle(articleId)
        }
    }
}