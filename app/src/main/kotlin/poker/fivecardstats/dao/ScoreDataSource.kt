package poker.fivecardstats.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import poker.fivecardstats.helper.MySQLiteHelper
import poker.fivecardstats.model.Score
import java.sql.SQLException

class ScoreDataSource(c: Context) {
    private lateinit var db: SQLiteDatabase
    private val dbHelper: MySQLiteHelper = MySQLiteHelper(c)

    @Throws(SQLException::class)
    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun create(id: Long, type: String, score: Int): Boolean {
        val values = ContentValues().apply {
            put(MySQLiteHelper.COLUMN_USER_ID, id)
            put(MySQLiteHelper.COLUMN_TYPE, type)
            put(MySQLiteHelper.COLUMN_SCORE, score)
        }
        return db.insert(MySQLiteHelper.TABLE_SCORE, null, values) > 0
    }

    fun delete(uId: Long) {
        val query = "SELECT * FROM ${MySQLiteHelper.TABLE_SCORE};"
        db.rawQuery(query, null).use { c ->
            if (c.moveToFirst()) {
                do {
                    val id = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID))
                    val userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID))

                    if (uId != userId) {
                        db.delete(MySQLiteHelper.TABLE_SCORE,
                                "${MySQLiteHelper.COLUMN_ID}=?",
                                arrayOf(id.toString()))
                    }
                } while (c.moveToNext())
            }
        }
    }

    fun getScores(uId: Long): List<Score> {
        val scores = mutableListOf<Score>()
        val query = "SELECT * FROM ${MySQLiteHelper.TABLE_SCORE};"
        db.rawQuery(query, null).use { c ->
            if (c.moveToFirst()) {
                do {
                    val id = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID))
                    val userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID))
                    val type = c.getString(c.getColumnIndex(MySQLiteHelper.COLUMN_TYPE))
                    val score = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_SCORE))

                    if (uId == userId) {
                        scores.add(Score(id, userId, type, score))
                    }
                } while (c.moveToNext())
            }
        }
        return scores
    }
}