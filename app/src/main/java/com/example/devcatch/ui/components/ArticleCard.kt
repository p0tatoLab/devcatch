package com.example.devcatch.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.devcatch.domain.model.ArticleWithAnalysis
import com.example.devcatch.domain.model.Difficulty
import com.example.devcatch.util.TimeUtils.toRelativeTime

/**
 * 記事カード
 */
@Composable
fun ArticleCard(
    article: ArticleWithAnalysis,
    onArticleClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onArticleClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // ヘッダー: ソース + 公開日
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // ソース
                Text(
                    text = article.source,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                // 公開日
                Text(
                    text = article.publishedAt.toRelativeTime(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // タイトル
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // AI要約（あれば）
            article.analysis?.oneLinerSummary?.let { summary ->
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "AI要約",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = summary,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // メタ情報
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // バッジ（難易度、読了時間など）
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // カテゴリ
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = article.category.displayName,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )

                    // 難易度（AI分析があれば）
                    article.analysis?.difficulty?.let { difficulty ->
                        DifficultyBadge(difficulty)
                    }

                    // 読了時間（AI分析があれば）
                    article.analysis?.estimatedReadingTime?.let { time ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = "読了時間",
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "${time}分",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // ブックマークボタン
                IconButton(onClick = onBookmarkClick) {
                    Icon(
                        imageVector = if (article.isBookmarked)
                            Icons.Filled.Bookmark
                        else
                            Icons.Filled.BookmarkBorder,
                        contentDescription = "ブックマーク",
                        tint = if (article.isBookmarked)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 未読バッジ
            if (!article.isRead) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Text(
                        text = "未読",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

/**
 * 難易度バッジ
 */
@Composable
fun DifficultyBadge(difficulty: Difficulty) {
    val (text, color) = when (difficulty) {
        Difficulty.BEGINNER -> "初級" to MaterialTheme.colorScheme.tertiary
        Difficulty.INTERMEDIATE -> "中級" to MaterialTheme.colorScheme.secondary
        Difficulty.ADVANCED -> "上級" to MaterialTheme.colorScheme.error
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}