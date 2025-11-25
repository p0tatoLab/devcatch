package com.example.devcatch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.devcatch.ui.screens.bookmarks.BookmarksScreen
import com.example.devcatch.ui.screens.detail.ArticleDetailScreen
import com.example.devcatch.ui.screens.feed.FeedScreen
import com.example.devcatch.ui.screens.search.SearchScreen
import com.example.devcatch.ui.screens.settings.SettingsScreen
import com.example.devcatch.ui.screens.trends.TrendsScreen

/**
 * アプリのナビゲーショングラフ
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Feed.route,
        modifier = modifier
    ) {
        // フィード画面
        composable(route = Screen.Feed.route) {
            FeedScreen(
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                },
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // トレンド画面
        composable(route = Screen.Trends.route) {
            TrendsScreen(
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                }
            )
        }

        // 検索画面
        composable(route = Screen.Search.route) {
            SearchScreen(
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ブックマーク画面
        composable(route = Screen.Bookmarks.route) {
            BookmarksScreen(
                onArticleClick = { articleId ->
                    navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                }
            )
        }

        // 記事詳細画面
        composable(
            route = Screen.ArticleDetail.route,
            arguments = listOf(
                navArgument("articleId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getString("articleId") ?: ""
            ArticleDetailScreen(
                articleId = articleId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // 設定画面
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSourceManagement = {
                    navController.navigate(Screen.SourceManagement.route)
                },
                onNavigateToNotificationSettings = {
                    navController.navigate(Screen.NotificationSettings.route)
                }
            )
        }
    }
}