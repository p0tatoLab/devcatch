package com.example.devcatch.ui.screens.trends

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devcatch.domain.model.EmergingLibrary
import com.example.devcatch.domain.model.TopicTrend
import com.example.devcatch.domain.model.Trend
import com.example.devcatch.util.TimeUtils.toDateString

/**
 * トレンド画面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendsScreen(
    onArticleClick: (String) -> Unit,
    viewModel: TrendsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("トレンド") },
                actions = {
                    IconButton(onClick = { viewModel.loadTrendReport() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "更新")
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

            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "エラー: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.loadTrendReport() }) {
                            Text("再試行")
                        }
                    }
                }
            }

            uiState.trendReport != null -> {
                TrendReportContent(
                    trendReport = uiState.trendReport!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * トレンドレポートコンテンツ
 */
@Composable
fun TrendReportContent(
    trendReport: com.example.devcatch.domain.model.TrendReport,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // レポート生成日時
        Text(
            text = "最終更新: ${trendReport.generatedAt.toDateString()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // 週次サマリー
        WeekSummaryCard(summary = trendReport.weekSummary)

        // トップトピック
        if (trendReport.topTopics.isNotEmpty()) {
            TopTopicsSection(topTopics = trendReport.topTopics)
        }

        // 注目ライブラリ
        if (trendReport.emergingLibraries.isNotEmpty()) {
            EmergingLibrariesSection(libraries = trendReport.emergingLibraries)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * 週次サマリーカード
 */
@Composable
fun WeekSummaryCard(summary: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Summarize,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "今週のサマリー",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                text = summary,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

/**
 * トップトピックセクション
 */
@Composable
fun TopTopicsSection(topTopics: List<TopicTrend>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "トップトピック",
            style = MaterialTheme.typography.titleLarge
        )

        topTopics.forEach { topic ->
            TopicTrendCard(topic = topic)
        }
    }
}

/**
 * トピックトレンドカード
 */
@Composable
fun TopicTrendCard(topic: TopicTrend) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = topic.topic,
                        style = MaterialTheme.typography.titleMedium
                    )
                    TrendBadge(trend = topic.trend)
                }
                Text(
                    text = topic.summary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${topic.count}件の記事",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // 重要度インジケーター
            ImportanceIndicator(importance = topic.importance)
        }
    }
}

/**
 * トレンドバッジ
 */
@Composable
fun TrendBadge(trend: Trend) {
    val (icon, color, text) = when (trend) {
        Trend.RISING -> Triple(
            Icons.Default.TrendingUp,
            MaterialTheme.colorScheme.tertiary,
            "上昇中"
        )
        Trend.STABLE -> Triple(
            Icons.Default.TrendingFlat,
            MaterialTheme.colorScheme.secondary,
            "安定"
        )
        Trend.DECLINING -> Triple(
            Icons.Default.TrendingDown,
            MaterialTheme.colorScheme.error,
            "下降中"
        )
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = color
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}

/**
 * 重要度インジケーター
 */
@Composable
fun ImportanceIndicator(importance: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = importance.toString(),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "重要度",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * 注目ライブラリセクション
 */
@Composable
fun EmergingLibrariesSection(libraries: List<EmergingLibrary>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "注目ライブラリ・技術",
            style = MaterialTheme.typography.titleLarge
        )

        libraries.forEach { library ->
            EmergingLibraryCard(library = library)
        }
    }
}

/**
 * 注目ライブラリカード
 */
@Composable
fun EmergingLibraryCard(library: EmergingLibrary) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.NewReleases,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = library.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = library.purpose,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = library.whyMatters,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}