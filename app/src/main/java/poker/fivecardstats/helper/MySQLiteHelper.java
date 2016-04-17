package poker.fivecardstats.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_SCORE = "score";
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_SCORE = "score";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_USER = "create table "
            + TABLE_USER + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text, "
            + COLUMN_SCORE + " text);";

    private static final String DATABASE_CREATE_SCORE = "create table "
            + TABLE_SCORE + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_USER_ID + " text, "
            + COLUMN_TYPE + " text, "
            + COLUMN_SCORE + " text);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_USER);
        db.execSQL(DATABASE_CREATE_SCORE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }
}
