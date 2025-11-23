package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.repository.NewsSourceRepository
import javax.inject.Inject

/**
 * ニュースソースの有効/無効を切り替えるUseCase
 */
class ToggleNewsSourceUseCase @Inject constructor(
    private val repository: NewsSourceRepository
) {
    suspend operator fun invoke(sourceId: String) {
        repository.toggleSourceEnabled(sourceId)
    }
}