package poker.fivecardstats.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import poker.fivecardstats.helper.MySQLiteHelper
import poker.fivecardstats.model.User
import java.sql.SQLException

class UserDataSource(c: Context) {
    private lateinit var db: SQLiteDatabase
    private val dbHelper: MySQLiteHelper = MySQLiteHelper(c)

    @Throws(SQLException::class)
    fun open() {
        db = dbHelper.writableDatabase
    }

    fun create(name: String): Boolean {
        val values = ContentValues().apply {
            put(MySQLiteHelper.COLUMN_NAME, name)
        }
        return db.insert(MySQLiteHelper.TABLE_USER, null, values) > 0
    }

    fun update(name: String, uId: Long) {
        db.rawQuery("SELECT * FROM ${MySQLiteHelper.TABLE_USER}", null).use { c ->
            if (c.moveToFirst()) {
                val userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID))
                if (userId == uId) {
                    val where = "${MySQLiteHelper.COLUMN_ID} = ?"
                    val whereArgs = arrayOf(userId.toString())
                    val values = ContentValues().apply {
                        put(MySQLiteHelper.COLUMN_NAME, name)
                    }
                    db.update(MySQLiteHelper.TABLE_USER, values, where, whereArgs)
                }
            } else {
                create(name)
            }
        }
    }

    fun delete(uId: Long) {
        db.delete(MySQLiteHelper.TABLE_USER,
                "${MySQLiteHelper.COLUMN_ID}=?",
                arrayOf(uId.toString()))

        val query = "SELECT * FROM ${MySQLiteHelper.TABLE_SCORE};"
        db.rawQuery(query, null).use { c ->
            if (c.moveToFirst()) {
                do {
                    val id = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID))
                    val userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID))
                    if (uId == userId) {
                        db.delete(MySQLiteHelper.TABLE_SCORE,
                                "${MySQLiteHelper.COLUMN_ID}=?",
                                arrayOf(id.toString()))
                    }
                } while (c.moveToNext())
            }
        }
    }

    fun resetAllScores() {
        db.delete(MySQLiteHelper.TABLE_SCORE, null, null)
    }

    val users: List<User>
        get() {
            val users = mutableListOf<User>()
            val queryUser = "SELECT * FROM ${MySQLiteHelper.TABLE_USER};"
            val queryScore = "SELECT * FROM ${MySQLiteHelper.TABLE_SCORE};"

            db.rawQuery(queryUser, null).use { cUser ->
                db.rawQuery(queryScore, null).use { cScore ->
                    if (cUser.moveToFirst()) {
                        do {
                            val id = cUser.getLong(cUser.getColumnIndex(MySQLiteHelper.COLUMN_ID))
                            val name = cUser.getString(cUser.getColumnIndex(MySQLiteHelper.COLUMN_NAME))
                            var score = 0
                            if (cScore.moveToFirst()) {
                                do {
                                    val userId = cScore.getLong(cScore.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID))
                                    val points = cScore.getInt(cScore.getColumnIndex(MySQLiteHelper.COLUMN_SCORE))
                                    if (id == userId) {
                                        score += points
                                    }
                                } while (cScore.moveToNext())
                            }
                            users.add(User(id, name, score))
                        } while (cUser.moveToNext())
                    }
                }
            }
            return users
        }
}