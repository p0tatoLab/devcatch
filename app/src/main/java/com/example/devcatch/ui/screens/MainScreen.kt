package com.example.devcatch.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.devcatch.ui.navigation.NavGraph
import com.example.devcatch.ui.navigation.Screen

/**
 * メイン画面（Scaffold + BottomNavigation）
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // ボトムナビゲーションに表示する画面
    val bottomNavItems = listOf(
        BottomNavItem.Feed,
        BottomNavItem.Trends,
        BottomNavItem.Search,
        BottomNavItem.Bookmarks
    )

    // ボトムナビゲーションを表示するかどうか
    val showBottomBar = currentDestination?.route in bottomNavItems.map { it.screen.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    // 同じ画面を複数回開かないようにする
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // 状態を保存
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            NavGraph(navController = navController)
        }
    }
}

/**
 * ボトムナビゲーションのアイテム
 */
sealed class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
) {
    object Feed : BottomNavItem(
        screen = Screen.Feed,
        icon = Icons.Default.Home,
        label = "フィード"
    )

    object Trends : BottomNavItem(
        screen = Screen.Trends,
        icon = Icons.Default.TrendingUp,
        label = "トレンド"
    )

    object Search : BottomNavItem(
        screen = Screen.Search,
        icon = Icons.Default.Search,
        label = "検索"
    )

    object Bookmarks : BottomNavItem(
        screen = Screen.Bookmarks,
        icon = Icons.Default.Bookmark,
        label = "保存"
    )
}