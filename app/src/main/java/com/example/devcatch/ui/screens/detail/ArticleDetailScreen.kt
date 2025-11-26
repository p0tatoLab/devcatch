package com.example.devcatch.ui.screens.detail

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devcatch.ui.components.AIAnalysisSection
import com.example.devcatch.util.TimeUtils.toDateTimeString

/**
 * 記事詳細画面
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    articleId: String,
    onNavigateBack: () -> Unit,
    viewModel: ArticleDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("記事詳細") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                },
                actions = {
                    // ブックマークボタン
                    IconButton(
                        onClick = { viewModel.toggleBookmark() }
                    ) {
                        Icon(
                            imageVector = if (uiState.article?.isBookmarked == true)
                                Icons.Filled.Bookmark
                            else
                                Icons.Filled.BookmarkBorder,
                            contentDescription = "ブックマーク",
                            tint = if (uiState.article?.isBookmarked == true)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // 共有ボタン
                    IconButton(
                        onClick = {
                            uiState.article?.let { article ->
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, article.title)
                                    putExtra(Intent.EXTRA_TEXT, "${article.title}\n\n${article.url}")
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "記事を共有"))
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "共有"
                        )
                    }

                    // ブラウザで開くボタン
                    IconButton(
                        onClick = {
                            uiState.article?.let { article ->
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                                context.startActivity(browserIntent)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = "ブラウザで開く"
                        )
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
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = uiState.error ?: "エラーが発生しました",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Button(onClick = onNavigateBack) {
                            Text("戻る")
                        }
                    }
                }
            }

            uiState.article != null -> {
                ArticleDetailContent(
                    article = uiState.article!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

/**
 * 記事詳細コンテンツ
 */
@Composable
fun ArticleDetailContent(
    article: com.example.devcatch.domain.model.ArticleWithAnalysis,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // タイトル
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineMedium
        )

        // メタ情報（ソース、カテゴリ、日時）
        ArticleMetaInfo(article = article)

        Divider()

        // AI分析セクション
        if (article.analysis != null) {
            AIAnalysisSection(analysis = article.analysis)

            Divider()
        }

        // 記事本文
        ArticleContent(article = article)

        // タグ（AI分析があれば）
        if (article.analysis?.tags?.isNotEmpty() == true) {
            Divider()
            ArticleTags(tags = article.analysis!!.tags)
        }

        // 関連技術（AI分析があれば）
        if (article.analysis?.relatedTechnologies?.isNotEmpty() == true) {
            RelatedTechnologies(technologies = article.analysis!!.relatedTechnologies)
        }

        // 前提知識（AI分析があれば）
        if (article.analysis?.prerequisites?.isNotEmpty() == true) {
            Prerequisites(prerequisites = article.analysis!!.prerequisites)
        }

        // 公式リソース（AI分析があれば）
        if (article.analysis?.officialResource != null) {
            OfficialResource(url = article.analysis!!.officialResource!!)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * 記事メタ情報
 */
@Composable
fun ArticleMetaInfo(article: com.example.devcatch.domain.model.ArticleWithAnalysis) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // ソース
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Source,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = article.source,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // カテゴリ
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = article.category.icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = article.category.displayName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // 公開日時
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = article.publishedAt.toDateTimeString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * 記事本文
 */
@Composable
fun ArticleContent(article: com.example.devcatch.domain.model.ArticleWithAnalysis) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "本文",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = article.content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * タグ
 */
@Composable
fun ArticleTags(tags: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "タグ",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            tags.forEach { tag ->
                AssistChip(
                    onClick = { },
                    label = { Text(tag) }
                )
            }
        }
    }
}

/**
 * 関連技術
 */
@Composable
fun RelatedTechnologies(technologies: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "関連技術",
            style = MaterialTheme.typography.titleMedium
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            technologies.forEach { tech ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = tech,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

/**
 * 前提知識
 */
@Composable
fun Prerequisites(prerequisites: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "前提知識",
            style = MaterialTheme.typography.titleMedium
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            prerequisites.forEach { prerequisite ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = prerequisite,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

/**
 * 公式リソース
 */
@Composable
fun OfficialResource(url: String) {
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "公式リソース",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedButton(
            onClick = {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(browserIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Link,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("公式ドキュメントを開く")
        }
    }
}