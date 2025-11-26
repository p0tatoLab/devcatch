package com.example.devcatch.data.repository

import com.example.devcatch.domain.model.*
import java.util.concurrent.TimeUnit

/**
 * テストデータを生成
 */
object TestDataGenerator {

    /**
     * テスト用の記事を生成
     */
    fun generateTestArticles(): List<Article> {
        val now = System.currentTimeMillis()

        return listOf(
            Article(
                id = "1",
                title = "Jetpack Composeの新機能が発表 - より効率的なUI開発が可能に",
                url = "https://example.com/article/1",
                source = "Android Developers Blog",
                sourceFavicon = null,
                category = Category.JETPACK_COMPOSE,
                publishedAt = now - TimeUnit.HOURS.toMillis(2),
                content = "GoogleはJetpack Composeの新しいバージョンを発表しました。この更新により、開発者はより効率的にUIを構築できるようになります。新しいAPIにより、アニメーションの実装が簡単になり、パフォーマンスも大幅に向上しました。",
                summary = "Jetpack Composeの新バージョンが発表され、UI開発の効率が向上",
                imageUrl = null,
                isBookmarked = false,
                isRead = false
            ),

            Article(
                id = "2",
                title = "Kotlin 2.0のロードマップが公開 - 新しい言語機能に期待",
                url = "https://example.com/article/2",
                source = "Kotlin Blog",
                sourceFavicon = null,
                category = Category.KOTLIN,
                publishedAt = now - TimeUnit.HOURS.toMillis(5),
                content = "JetBrainsがKotlin 2.0のロードマップを公開しました。新しいコンパイラの最適化、コルーチンの改善、そして新しい言語機能が含まれています。特に、型推論の改善とnull安全性の強化が注目されています。",
                summary = "Kotlin 2.0のロードマップ公開、コンパイラ最適化と新機能を予定",
                imageUrl = null,
                isBookmarked = true,
                isRead = false
            ),

            Article(
                id = "3",
                title = "AndroidアプリのMaterial Design 3対応ガイド",
                url = "https://example.com/article/3",
                source = "Material Design Blog",
                sourceFavicon = null,
                category = Category.MATERIAL_DESIGN,
                publishedAt = now - TimeUnit.HOURS.toMillis(12),
                content = "Material Design 3への移行を支援する詳細なガイドが公開されました。既存のアプリをMaterial You対応にする手順、新しいコンポーネントの使い方、カラーシステムの適用方法などが解説されています。",
                summary = "Material Design 3への移行ガイドが公開",
                imageUrl = null,
                isBookmarked = false,
                isRead = true
            ),

            Article(
                id = "4",
                title = "Room DatabaseとFlowで実現するリアクティブなデータ管理",
                url = "https://example.com/article/4",
                source = "Medium - Android",
                sourceFavicon = null,
                category = Category.ARCHITECTURE,
                publishedAt = now - TimeUnit.DAYS.toMillis(1),
                content = "RoomとKotlin Flowを組み合わせることで、リアクティブなデータ管理が可能になります。この記事では、実際のアプリ開発での実装例を交えながら、ベストプラクティスを紹介します。",
                summary = "RoomとFlowを使ったリアクティブなデータ管理の実装方法",
                imageUrl = null,
                isBookmarked = false,
                isRead = false
            ),

            Article(
                id = "5",
                title = "Hiltによる依存性注入の完全ガイド",
                url = "https://example.com/article/5",
                source = "DEV.to - Android",
                sourceFavicon = null,
                category = Category.ARCHITECTURE,
                publishedAt = now - TimeUnit.DAYS.toMillis(2),
                content = "Hiltを使った依存性注入の実装方法を、基礎から応用まで詳しく解説します。ViewModelへの注入、テストでのモック、カスタムスコープの作成など、実践的な内容を網羅しています。",
                summary = "Hiltによる依存性注入の基礎から応用まで",
                imageUrl = null,
                isBookmarked = true,
                isRead = false
            ),

            Article(
                id = "6",
                title = "Androidアプリのパフォーマンス最適化テクニック",
                url = "https://example.com/article/6",
                source = "Android Developers Blog",
                sourceFavicon = null,
                category = Category.PERFORMANCE,
                publishedAt = now - TimeUnit.DAYS.toMillis(3),
                content = "アプリのパフォーマンスを改善するための具体的なテクニックを紹介します。起動時間の短縮、メモリ使用量の削減、レイアウトの最適化など、実測データとともに解説します。",
                summary = "アプリパフォーマンス向上のための実践的テクニック",
                imageUrl = null,
                isBookmarked = false,
                isRead = false
            ),

            Article(
                id = "7",
                title = "Kotlin Coroutinesの深堀り - 非同期処理のベストプラクティス",
                url = "https://example.com/article/7",
                source = "Kotlin Blog",
                sourceFavicon = null,
                category = Category.KOTLIN,
                publishedAt = now - TimeUnit.DAYS.toMillis(4),
                content = "Kotlin Coroutinesの高度な使い方を解説します。構造化された並行性、エラーハンドリング、キャンセレーション、テストの書き方など、実務で役立つ知識を詳しく説明します。",
                summary = "Coroutinesの高度な使い方とベストプラクティス",
                imageUrl = null,
                isBookmarked = false,
                isRead = false
            ),

            Article(
                id = "8",
                title = "Compose NavigationでのDeep Link実装",
                url = "https://example.com/article/8",
                source = "Medium - Android",
                sourceFavicon = null,
                category = Category.JETPACK_COMPOSE,
                publishedAt = now - TimeUnit.DAYS.toMillis(5),
                content = "Jetpack Compose NavigationでDeep Linkを実装する方法を、実例を交えて解説します。AndroidManifestの設定、NavGraphでのDeep Link定義、動的なリンクの処理などを網羅します。",
                summary = "Compose NavigationでのDeep Link実装ガイド",
                imageUrl = null,
                isBookmarked = false,
                isRead = false
            ),

            Article(
                id = "9",
                title = "Android 15の新機能プレビュー",
                url = "https://example.com/article/9",
                source = "Android Developers Blog",
                sourceFavicon = null,
                category = Category.ANDROID_OS,
                publishedAt = now - TimeUnit.DAYS.toMillis(6),
                content = "Android 15の新機能が発表されました。プライバシー保護の強化、バッテリー最適化の改善、新しいUI要素など、開発者が知っておくべき変更点をまとめて紹介します。",
                summary = "Android 15の新機能と変更点のプレビュー",
                imageUrl = null,
                isBookmarked = false,
                isRead = true
            ),

            Article(
                id = "10",
                title = "Kotlin Multiplatform Mobileの始め方",
                url = "https://example.com/article/10",
                source = "Kotlin Blog",
                sourceFavicon = null,
                category = Category.KMP,
                publishedAt = now - TimeUnit.DAYS.toMillis(7),
                content = "Kotlin Multiplatform Mobile (KMM)を使って、AndroidとiOSでコードを共有する方法を解説します。プロジェクトのセットアップから、共通ロジックの実装、プラットフォーム固有の処理まで、ステップバイステップで説明します。",
                summary = "KMMでAndroid/iOSのコード共有を始めるガイド",
                imageUrl = null,
                isBookmarked = true,
                isRead = false
            )
        )
    }

    /**
     * テスト用のAI分析データを生成
     */
    fun generateTestAnalysis(): List<ArticleAnalysis> {
        return listOf(
            ArticleAnalysis(
                articleId = "1",
                oneLinerSummary = "Compose新機能でUI開発が効率化",
                detailedSummary = "Jetpack Composeの新バージョンが発表され、UI開発の効率が大幅に向上しました。新しいAPIによりアニメーション実装が簡単になり、パフォーマンスも改善されています。",
                keyPoints = listOf(
                    "新しいAPIでアニメーションが簡単に",
                    "パフォーマンスの大幅な向上",
                    "開発効率の改善"
                ),
                shouldRead = ShouldRead(
                    verdict = true,
                    reason = "Composeを使っている開発者は必読。最新の機能を理解することで、より効率的な開発が可能になります。"
                ),
                difficulty = Difficulty.INTERMEDIATE,
                estimatedReadingTime = 5,
                practicalityLevel = PracticalityLevel.PRACTICAL,
                timelineRelevance = TimelineRelevance.IMMEDIATE,
                learningPriority = 9,
                relatedTechnologies = listOf("Jetpack Compose", "Android UI", "Kotlin"),
                tags = listOf("Compose", "UI", "新機能"),
                relevanceScore = 9,
                actionItems = listOf(
                    "新しいAPIを既存プロジェクトに適用",
                    "アニメーションのパフォーマンスを測定"
                ),
                deprecationWarning = null,
                prerequisites = listOf("Jetpack Composeの基礎知識"),
                officialResource = "https://developer.android.com/jetpack/compose"
            ),

            ArticleAnalysis(
                articleId = "2",
                oneLinerSummary = "Kotlin 2.0で型推論とnull安全性が強化",
                detailedSummary = "Kotlin 2.0のロードマップが公開され、コンパイラの最適化、コルーチンの改善、新しい言語機能が予定されています。特に型推論の改善とnull安全性の強化が注目されています。",
                keyPoints = listOf(
                    "コンパイラの最適化",
                    "型推論の改善",
                    "null安全性の強化"
                ),
                shouldRead = ShouldRead(
                    verdict = true,
                    reason = "Kotlinを使用するすべての開発者にとって重要な情報。将来の言語仕様を理解することで、より良いコードが書けるようになります。"
                ),
                difficulty = Difficulty.INTERMEDIATE,
                estimatedReadingTime = 7,
                practicalityLevel = PracticalityLevel.THEORETICAL,
                timelineRelevance = TimelineRelevance.FUTURE,
                learningPriority = 8,
                relatedTechnologies = listOf("Kotlin", "コンパイラ", "型システム"),
                tags = listOf("Kotlin", "2.0", "ロードマップ"),
                relevanceScore = 8,
                actionItems = listOf(
                    "既存コードで改善される部分を確認",
                    "新機能のプレビュー版を試す"
                ),
                deprecationWarning = null,
                prerequisites = listOf("Kotlinの基礎知識"),
                officialResource = "https://kotlinlang.org"
            )
        )
    }
}