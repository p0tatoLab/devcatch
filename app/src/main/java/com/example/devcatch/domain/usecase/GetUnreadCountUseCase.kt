package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 未読記事数を取得するUseCase
 */
class GetUnreadCountUseCase @Inject constructor(
    private val repository: ArticleRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.getUnreadCount()
    }
}