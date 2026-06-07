package com.ssk.pagekeeper.core.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * v1 → v2: added per-book status flags. Once shipped, this migration is frozen —
 * never edit it. Future schema changes should be `@AutoMigration` on [BookDatabase].
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE books ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE books ADD COLUMN isFinished INTEGER NOT NULL DEFAULT 0")
    }
}
