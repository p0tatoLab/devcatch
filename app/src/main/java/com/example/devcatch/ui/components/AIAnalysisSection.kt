package com.example.devcatch.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.devcatch.domain.model.ArticleAnalysis

/**
 * AI分析セクション
 */
@Composable
fun AIAnalysisSection(
    analysis: ArticleAnalysis?,
    modifier: Modifier = Modifier
) {
    if (analysis == null) return

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // セクションタイトル
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI分析",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "AI分析",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Divider()

            // 詳細要約
            AnalysisItem(
                icon = Icons.Default.Description,
                title = "要約",
                content = analysis.detailedSummary
            )

            // 読むべきか判定
            ShouldReadCard(shouldRead = analysis.shouldRead)

            // 重要ポイント
            if (analysis.keyPoints.isNotEmpty()) {
                AnalysisItem(
                    icon = Icons.Default.Lightbulb,
                    title = "重要ポイント"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        analysis.keyPoints.forEach { point ->
                            KeyPointItem(point = point)
                        }
                    }
                }
            }

            // すぐ試せること
            if (analysis.actionItems.isNotEmpty()) {
                AnalysisItem(
                    icon = Icons.Default.CheckCircle,
                    title = "すぐ試せること"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        analysis.actionItems.forEach { action ->
                            ActionItem(action = action)
                        }
                    }
                }
            }

            // メタ情報グリッド
            MetaInfoGrid(analysis = analysis)
        }
    }
}

/**
 * 分析項目
 */
@Composable
fun AnalysisItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    content: String? = null,
    contentComposable: (@Composable () -> Unit)? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (content != null) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        contentComposable?.invoke()
    }
}

/**
 * 読むべきか判定カード
 */
@Composable
fun ShouldReadCard(shouldRead: com.example.devcatch.domain.model.ShouldRead) {
    Surface(
        color = if (shouldRead.verdict)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.errorContainer,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = if (shouldRead.verdict)
                    Icons.Default.ThumbUp
                else
                    Icons.Default.ThumbDown,
                contentDescription = null,
                tint = if (shouldRead.verdict)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onErrorContainer
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = if (shouldRead.verdict) "読むことをおすすめします" else "スキップしてもOK",
                    style = MaterialTheme.typography.titleSmall,
                    color = if (shouldRead.verdict)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onErrorContainer
                )
                Text(
                    text = shouldRead.reason,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (shouldRead.verdict)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

/**
 * 重要ポイント項目
 */
@Composable
fun KeyPointItem(point: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            modifier = Modifier.size(8.dp).padding(top = 6.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = point,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * アクション項目
 */
@Composable
fun ActionItem(action: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(16.dp).padding(top = 2.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = action,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * メタ情報グリッド
 */
@Composable
fun MetaInfoGrid(analysis: ArticleAnalysis) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "詳細情報",
            style = MaterialTheme.typography.titleMedium
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MetaInfoRow(
                label = "難易度",
                value = analysis.difficulty.displayName
            )
            MetaInfoRow(
                label = "読了時間",
                value = "${analysis.estimatedReadingTime}分"
            )
            MetaInfoRow(
                label = "実践度",
                value = analysis.practicalityLevel.displayName
            )
            MetaInfoRow(
                label = "時間軸",
                value = analysis.timelineRelevance.displayName
            )
            MetaInfoRow(
                label = "学習優先度",
                value = "${analysis.learningPriority}/10"
            )
            MetaInfoRow(
                label = "重要度",
                value = "${analysis.relevanceScore}/10"
            )
        }
    }
}

/**
 * メタ情報行
 */
@Composable
fun MetaInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}