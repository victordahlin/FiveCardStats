package poker.fivecardstats.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MySQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DATABASE_CREATE_USER)
        db.execSQL(DATABASE_CREATE_SCORE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(MySQLiteHelper::class.java.name,
                "Upgrading database from version $oldVersion to $newVersion, which will destroy all old data")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SCORE")
        onCreate(db)
    }

    companion object {
        const val TABLE_SCORE = "score"
        const val TABLE_USER = "user"
        const val COLUMN_ID = "_id"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_TYPE = "type"
        const val COLUMN_SCORE = "score"
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1

        private const val DATABASE_CREATE_USER = "create table $TABLE_USER($COLUMN_ID integer primary key autoincrement, $COLUMN_NAME text, $COLUMN_SCORE text);"

        private const val DATABASE_CREATE_SCORE = "create table $TABLE_SCORE($COLUMN_ID integer primary key autoincrement, $COLUMN_USER_ID text, $COLUMN_TYPE text, $COLUMN_SCORE text);"
    }
}