package com.example.devcatch.data.remote.dto

/**
 * RSSアイテムのDTO
 */
data class RssItemDto(
    val title: String,
    val link: String,
    val description: String?,
    val pubDate: String?,
    val author: String?,
    val categories: List<String>
)