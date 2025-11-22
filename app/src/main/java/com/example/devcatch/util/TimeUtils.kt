package com.example.devcatch.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * 時間関連のユーティリティ
 */
object TimeUtils {

    /**
     * Unix timestampを相対時間表示に変換
     * 例: "3時間前", "2日前", "1週間前"
     */
    fun Long.toRelativeTime(): String {
        val now = System.currentTimeMillis()
        val diff = now - this

        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "たった今"
            diff < TimeUnit.HOURS.toMillis(1) -> {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                "${minutes}分前"
            }
            diff < TimeUnit.DAYS.toMillis(1) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(diff)
                "${hours}時間前"
            }
            diff < TimeUnit.DAYS.toMillis(7) -> {
                val days = TimeUnit.MILLISECONDS.toDays(diff)
                "${days}日前"
            }
            diff < TimeUnit.DAYS.toMillis(30) -> {
                val weeks = TimeUnit.MILLISECONDS.toDays(diff) / 7
                "${weeks}週間前"
            }
            diff < TimeUnit.DAYS.toMillis(365) -> {
                val months = TimeUnit.MILLISECONDS.toDays(diff) / 30
                "${months}ヶ月前"
            }
            else -> {
                val years = TimeUnit.MILLISECONDS.toDays(diff) / 365
                "${years}年前"
            }
        }
    }

    /**
     * Unix timestampを日付文字列に変換
     * 例: "2024年11月22日"
     */
    fun Long.toDateString(): String {
        val formatter = SimpleDateFormat("yyyy年MM月dd日", Locale.JAPANESE)
        return formatter.format(Date(this))
    }

    /**
     * Unix timestampを日時文字列に変換
     * 例: "2024/11/22 14:30"
     */
    fun Long.toDateTimeString(): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPANESE)
        return formatter.format(Date(this))
    }

    /**
     * ISO 8601形式の日付をUnix timestampに変換
     * 例: "2024-11-22T14:30:00Z" -> Unix timestamp
     */
    fun String.toUnixTimestamp(): Long {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            formatter.parse(this)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}