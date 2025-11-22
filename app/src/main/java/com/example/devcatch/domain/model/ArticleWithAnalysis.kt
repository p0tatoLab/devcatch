package com.example.devcatch.domain.model

/**
 * AI分析情報付きの記事
 */
data class ArticleWithAnalysis(
    val article: Article,
    val analysis: ArticleAnalysis?
) {
    // Article のプロパティへの便利なアクセス
    val id: String get() = article.id
    val title: String get() = article.title
    val url: String get() = article.url
    val source: String get() = article.source
    val sourceFavicon: String? get() = article.sourceFavicon
    val category: Category get() = article.category
    val publishedAt: Long get() = article.publishedAt
    val content: String get() = article.content
    val summary: String? get() = article.summary
    val imageUrl: String? get() = article.imageUrl
    val isBookmarked: Boolean get() = article.isBookmarked
    val isRead: Boolean get() = article.isRead
}