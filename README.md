# DevCatch 📰

Kotlin/Android開発の最新情報を集約し、AIで分析・要約するニュースアグリゲーター

## 概要

DevCatchは、複数のソース（RSS、GitHub、Reddit等）からKotlin/Android開発に関する最新情報を収集し、Claude AIによる要約・分析を提供することで、開発者の情報収集を効率化するアプリです。

## 主要機能

### Phase 1: MVP (Week 1-3)
- [ ] RSS/APIフィードパーサー
- [ ] 記事一覧表示
- [ ] カテゴリ別フィルター
- [ ] ブックマーク機能
- [ ] オフライン対応（キャッシング）

### Phase 2: AI機能 (Week 4-5)
- [ ] Claude API統合
- [ ] AI記事要約
- [ ] 重要度スコアリング
- [ ] 自動タグ付け
- [ ] 難易度判定

### Phase 3: 高度な機能 (Week 6-7)
- [ ] 検索機能（全文検索、セマンティック検索）
- [ ] トレンド分析
- [ ] 学習パス提案

### Phase 4: 通知とバックグラウンド処理 (Week 7-8)
- [ ] 15分ごとの自動更新
- [ ] カスタマイズ可能な通知
- [ ] 週次サマリーレポート

### Phase 5: UI/UX改善 (Week 8)
- [ ] Reader Mode
- [ ] アニメーション
- [ ] アクセシビリティ対応

## 技術スタック

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material Design 3
- **Architecture:** MVVM + Clean Architecture
- **DI:** Hilt
- **Database:** Room
- **Network:** Retrofit, OkHttp
- **RSS Parsing:** RSSParser
- **AI:** Claude API (Anthropic)
- **Image Loading:** Coil
- **Background Work:** WorkManager
- **Markdown:** Compose RichText

## セットアップ

### 前提条件
- Android Studio Hedgehog (2023.1.1) 以降
- JDK 17
- Android SDK (API 24以降)
- Claude API Key (Anthropic)

### API Keyの取得

1. [Anthropic Console](https://console.anthropic.com/) でアカウント作成
2. **API Keys** → **Create Key**
3. API Keyをコピー

### プロジェクトの設定

1. リポジトリをクローン
```bash
git clone https://github.com/YOUR_USERNAME/devcatch.git
cd devcatch
```

2. `local.properties` に以下を追加：
```properties
CLAUDE_API_KEY=YOUR_API_KEY_HERE
```

3. Android Studioでプロジェクトを開く

4. **Gradle Sync** を実行

5. エミュレータまたは実機で実行

## 情報ソース

### 公式
- Android Developers Blog
- Kotlin Blog (JetBrains)
- AOSP

### コミュニティ
- Medium (Android/Kotlin)
- Dev.to (Android/Kotlin)
- Reddit (r/androiddev, r/Kotlin)
- ProAndroidDev
- Styling Android

### GitHub
- GitHub Trending (Kotlin)
- 主要ライブラリのリリース

### 日本語
- Qiita (Android/Kotlin)
- Zenn (Android/Kotlin)

## 開発スケジュール

- **Week 1-3:** MVP（記事収集、表示、基本UI）
- **Week 4-5:** AI機能（要約、分析）
- **Week 6-7:** 検索、トレンド分析
- **Week 7-8:** 通知、バックグラウンド処理
- **Week 8:** 最終調整

## ライセンス

学習目的プロジェクト

## 作成者

学習プロジェクトとして作成