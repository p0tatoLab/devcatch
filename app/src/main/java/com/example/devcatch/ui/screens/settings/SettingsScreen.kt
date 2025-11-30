package com.example.devcatch.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devcatch.domain.model.NewsSource

/**
 * 設定画面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSourceManagement: () -> Unit,
    onNavigateToNotificationSettings: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設定") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    // アプリ情報セクション
                    item {
                        SettingsSectionHeader(title = "アプリ情報")
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.Article,
                            title = "記事数",
                            subtitle = "${uiState.articleCount}件",
                            onClick = null
                        )
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.Source,
                            title = "ニュースソース数",
                            subtitle = "${uiState.newsSources.size}個（有効: ${uiState.newsSources.count { it.isEnabled }}個）",
                            onClick = null
                        )
                    }

                    item {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }

                    // ニュースソースセクション
                    item {
                        SettingsSectionHeader(title = "ニュースソース")
                    }

                    items(
                        items = uiState.newsSources,
                        key = { it.id }
                    ) { source ->
                        NewsSourceItem(
                            source = source,
                            onToggle = { viewModel.toggleSource(source.id) }
                        )
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.RestartAlt,
                            title = "デフォルトに戻す",
                            subtitle = "すべてのソースをデフォルト設定に戻します",
                            onClick = { showResetDialog = true }
                        )
                    }

                    item {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }

                    // データ管理セクション
                    item {
                        SettingsSectionHeader(title = "データ管理")
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.DeleteSweep,
                            title = "古い記事を削除",
                            subtitle = "30日以上前の記事を削除します",
                            onClick = { viewModel.deleteOldArticles(30) }
                        )
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.DeleteForever,
                            title = "すべての記事を削除",
                            subtitle = "ブックマーク以外のすべての記事を削除します",
                            onClick = { showDeleteDialog = true },
                            isDestructive = true
                        )
                    }

                    item {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }

                    // 自動更新セクション
                    item {
                        SettingsSectionHeader(title = "自動更新")
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.Sync,
                            title = "自動更新",
                            subtitle = "6時間ごとに記事を自動取得します",
                            onClick = null  // 今は常にON（将来的にON/OFF機能を追加可能）
                        )
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.AutoDelete,
                            title = "自動クリーンアップ",
                            subtitle = "毎日、30日以上前の記事を自動削除します",
                            onClick = null
                        )
                    }

                    item {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }

                    // アプリ情報
                    item {
                        SettingsSectionHeader(title = "アプリについて")
                    }

                    item {
                        SettingsItem(
                            icon = Icons.Default.Info,
                            title = "バージョン",
                            subtitle = "1.0.0",
                            onClick = null
                        )
                    }
                }
            }
        }
    }

    // 削除確認ダイアログ
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = { Icon(Icons.Default.Warning, contentDescription = null) },
            title = { Text("すべての記事を削除") },
            text = { Text("ブックマーク以外のすべての記事を削除します。この操作は取り消せません。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAllArticles()
                        showDeleteDialog = false
                    }
                ) {
                    Text("削除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("キャンセル")
                }
            }
        )
    }

    // リセット確認ダイアログ
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            icon = { Icon(Icons.Default.RestartAlt, contentDescription = null) },
            title = { Text("デフォルトに戻す") },
            text = { Text("すべてのニュースソースをデフォルト設定に戻します。") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetToDefaultSources()
                        showResetDialog = false
                    }
                ) {
                    Text("リセット")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("キャンセル")
                }
            }
        )
    }
}

/**
 * セクションヘッダー
 */
@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

/**
 * 設定項目
 */
@Composable
fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)?,
    isDestructive: Boolean = false
) {
    val modifier = if (onClick != null) {
        Modifier.fillMaxWidth()
    } else {
        Modifier.fillMaxWidth()
    }

    Surface(
        onClick = onClick ?: {},
        enabled = onClick != null,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDestructive)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isDestructive)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * ニュースソース項目
 */
@Composable
fun NewsSourceItem(
    source: NewsSource,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = source.category.icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = source.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = source.category.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = source.updateFrequency.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Switch(
            checked = source.isEnabled,
            onCheckedChange = { onToggle() }
        )
    }
}