package com.example.devcatch.ui.navigation

/**
 * アプリ内の画面定義
 */
sealed class Screen(val route: String) {

    // ========== メイン画面 ==========

    /**
     * フィード画面（記事一覧）
     */
    object Feed : Screen("feed")

    /**
     * トレンド画面
     */
    object Trends : Screen("trends")

    /**
     * 検索画面
     */
    object Search : Screen("search")

    /**
     * ブックマーク画面
     */
    object Bookmarks : Screen("bookmarks")

    // ========== 詳細画面 ==========

    /**
     * 記事詳細画面
     */
    object ArticleDetail : Screen("article/{articleId}") {
        fun createRoute(articleId: String) = "article/$articleId"
    }

    // ========== 設定画面 ==========

    /**
     * 設定画面
     */
    object Settings : Screen("settings")

    /**
     * ニュースソース管理画面
     */
    object SourceManagement : Screen("source_management")

    /**
     * 通知設定画面
     */
    object NotificationSettings : Screen("notification_settings")
}