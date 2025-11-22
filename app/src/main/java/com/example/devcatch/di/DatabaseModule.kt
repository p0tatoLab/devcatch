package com.example.devcatch.di

import android.content.Context
import androidx.room.Room
import com.example.devcatch.data.local.dao.ArticleAnalysisDao
import com.example.devcatch.data.local.dao.ArticleDao
import com.example.devcatch.data.local.dao.NewsSourceDao
import com.example.devcatch.data.local.database.DevCatchDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Database を提供
     */
    @Provides
    @Singleton
    fun provideDevCatchDatabase(
        @ApplicationContext context: Context
    ): DevCatchDatabase {
        return Room.databaseBuilder(
            context,
            DevCatchDatabase::class.java,
            DevCatchDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration() // 開発中のみ使用
            .build()
    }

    /**
     * ArticleDao を提供
     */
    @Provides
    @Singleton
    fun provideArticleDao(database: DevCatchDatabase): ArticleDao {
        return database.articleDao()
    }

    /**
     * ArticleAnalysisDao を提供
     */
    @Provides
    @Singleton
    fun provideArticleAnalysisDao(database: DevCatchDatabase): ArticleAnalysisDao {
        return database.articleAnalysisDao()
    }

    /**
     * NewsSourceDao を提供
     */
    @Provides
    @Singleton
    fun provideNewsSourceDao(database: DevCatchDatabase): NewsSourceDao {
        return database.newsSourceDao()
    }
}