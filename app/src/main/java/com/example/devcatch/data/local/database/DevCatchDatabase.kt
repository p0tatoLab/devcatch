package com.example.devcatch.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.devcatch.data.local.dao.ArticleAnalysisDao
import com.example.devcatch.data.local.dao.ArticleDao
import com.example.devcatch.data.local.dao.NewsSourceDao
import com.example.devcatch.data.local.entity.ArticleAnalysisEntity
import com.example.devcatch.data.local.entity.ArticleEntity
import com.example.devcatch.data.local.entity.NewsSourceEntity

/**
 * DevCatch Database
 */
@Database(
    entities = [
        ArticleEntity::class,
        ArticleAnalysisEntity::class,
        NewsSourceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DevCatchDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
    abstract fun articleAnalysisDao(): ArticleAnalysisDao
    abstract fun newsSourceDao(): NewsSourceDao

    companion object {
        const val DATABASE_NAME = "devcatch_database"
    }
}