package com.example.devcatch.ui.screens.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devcatch.domain.model.Category
import com.example.devcatch.ui.components.ArticleCard

/**
 * フィード画面（記事一覧）
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    onArticleClick: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("DevCatch")
                        if (uiState.unreadCount > 0) {
                            Text(
                                text = "${uiState.unreadCount}件の未読記事",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(Icons.Default.Search, contentDescription = "検索")
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "設定")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // カテゴリフィルター
            CategoryFilterChips(
                selectedCategory = uiState.selectedCategory,
                onCategorySelected = { viewModel.selectCategory(it) }
            )

            // 記事一覧
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "エラー: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                uiState.articles.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "記事がありません",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = uiState.articles,
                            key = { it.id }
                        ) { article ->
                            ArticleCard(
                                article = article,
                                onArticleClick = {
                                    viewModel.markAsRead(article.id)
                                    onArticleClick(article.id)
                                },
                                onBookmarkClick = {
                                    viewModel.toggleBookmark(article.id)
                                },
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * カテゴリフィルターチップ
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterChips(
    selectedCategory: Category?,
    onCategorySelected: (Category?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("すべて") }
            )
        }

        items(
            items = Category.sortedByPriority().take(5), // 主要カテゴリのみ表示
            key = { it.name }
        ) { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.displayName) },
                leadingIcon = {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )
        }
    }
}