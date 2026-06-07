package com.ssk.pagekeeper.core.data.db

import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDatabaseMigrationTest {

    @get:Rule
    val helper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        BookDatabase::class.java,
    )

    @Test
    fun migrate1To2_seedsRowsRetainedAndFlagsDefaultFalse() {
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL(
                """
                INSERT INTO books (id, title, author, coverPath, filePath, dateAddedEpochMillis)
                VALUES ('id1', 'Title', 'Author', NULL, '/tmp/x.fb2', 0)
                """.trimIndent(),
            )
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)
        db.query("SELECT isFavorite, isFinished FROM books WHERE id = 'id1'").use { cursor ->
            assertEquals(true, cursor.moveToFirst())
            assertEquals(0, cursor.getInt(0))
            assertEquals(0, cursor.getInt(1))
        }
    }

    private companion object {
        const val TEST_DB = "migration-test.db"
    }
}
