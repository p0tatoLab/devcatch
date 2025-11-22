package com.example.devcatch.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 記事のカテゴリ
 */
enum class Category(
    val displayName: String,
    val icon: ImageVector,
    val priority: Int // 1-10, 高いほど重要
) {
    // 言語
    KOTLIN("Kotlin", Icons.Default.Code, 10),

    // フレームワーク/UI
    JETPACK_COMPOSE("Jetpack Compose", Icons.Default.Palette, 10),
    ANDROID_UI("Android UI", Icons.Default.Brush, 8),

    // Android OS/SDK
    ANDROID_SDK("Android SDK", Icons.Default.Android, 9),
    ANDROID_OS("Android OS", Icons.Default.PhoneAndroid, 8),

    // ライブラリ/ツール
    LIBRARIES("ライブラリ", Icons.Default.Extension, 9),
    TOOLS("開発ツール", Icons.Default.Build, 7),

    // アーキテクチャ
    ARCHITECTURE("アーキテクチャ", Icons.Default.AccountTree, 8),
    DESIGN_PATTERNS("デザインパターン", Icons.Default.Pattern, 7),

    // パフォーマンス
    PERFORMANCE("パフォーマンス", Icons.Default.Speed, 8),

    // セキュリティ
    SECURITY("セキュリティ", Icons.Default.Security, 9),

    // テスト
    TESTING("テスト", Icons.Default.BugReport, 7),

    // CI/CD
    CI_CD("CI/CD", Icons.Default.CloudSync, 6),

    // マルチプラットフォーム
    KMP("Kotlin Multiplatform", Icons.Default.Devices, 8),

    // デザイン
    UI_UX("UI/UX", Icons.Default.DesignServices, 7),
    MATERIAL_DESIGN("Material Design", Icons.Default.Colorize, 7),

    // Google公式
    GOOGLE_NEWS("Google公式", Icons.Default.Star, 10),

    // コミュニティ
    COMMUNITY("コミュニティ", Icons.Default.People, 6),

    // その他
    OTHER("その他", Icons.Default.Article, 5);

    companion object {
        /**
         * 表示名から検索
         */
        fun fromDisplayName(name: String): Category {
            return values().find { it.displayName == name } ?: OTHER
        }

        /**
         * 優先度順にソート
         */
        fun sortedByPriority(): List<Category> {
            return values().sortedByDescending { it.priority }
        }
    }
}