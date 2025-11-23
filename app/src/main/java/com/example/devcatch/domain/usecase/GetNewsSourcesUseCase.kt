package com.example.devcatch.domain.usecase

import com.example.devcatch.domain.model.NewsSource
import com.example.devcatch.domain.repository.NewsSourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * ニュースソースを取得するUseCase
 */
class GetNewsSourcesUseCase @Inject constructor(
    private val repository: NewsSourceRepository
) {
    operator fun invoke(): Flow<List<NewsSource>> {
        return repository.getAllSources()
    }

    fun getEnabledSources(): Flow<List<NewsSource>> {
        return repository.getEnabledSources()
    }
}