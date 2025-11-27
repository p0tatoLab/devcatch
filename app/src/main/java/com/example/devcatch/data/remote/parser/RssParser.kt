package com.example.devcatch.data.remote.parser

import com.example.devcatch.data.remote.dto.RssItemDto
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.model.RssChannel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * RSSフィードパーサー
 */
@Singleton
class RssFeedParser @Inject constructor() {

    private val parser = RssParser()

    /**
     * RSSフィードをパース
     */
    suspend fun parseRssFeed(url: String): Result<List<RssItemDto>> {
        return try {
            val channel: RssChannel = parser.getRssChannel(url)

            val items = channel.items.map { item ->
                RssItemDto(
                    title = item.title ?: "",
                    link = item.link ?: "",
                    description = item.description ?: "",
                    pubDate = item.pubDate,
                    author = item.author,
                    categories = item.categories
                )
            }

            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}