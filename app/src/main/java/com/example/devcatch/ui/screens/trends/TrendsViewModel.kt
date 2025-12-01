package com.example.devcatch.ui.screens.trends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devcatch.domain.usecase.GetTrendReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * トレンド画面のViewModel
 */
@HiltViewModel
class TrendsViewModel @Inject constructor(
    private val getTrendReportUseCase: GetTrendReportUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrendsUiState(isLoading = true))
    val uiState: StateFlow<TrendsUiState> = _uiState.asStateFlow()

    init {
        loadTrendReport()
    }

    /**
     * トレンドレポートを読み込む
     */
    fun loadTrendReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val report = getTrendReportUseCase()
                _uiState.update {
                    it.copy(
                        trendReport = report,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "トレンドレポートの取得に失敗しました"
                    )
                }
            }
        }
    }
}