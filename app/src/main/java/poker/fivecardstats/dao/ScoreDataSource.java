package poker.fivecardstats.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poker.fivecardstats.helper.MySQLiteHelper;
import poker.fivecardstats.model.Score;

/**
 * Created by Victor on 2016-04-11.
 */
public class ScoreDataSource {
    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_USER_ID,
            MySQLiteHelper.COLUMN_TYPE,
            MySQLiteHelper.COLUMN_SCORE,
    };

    public ScoreDataSource(Context c) {
        dbHelper = new MySQLiteHelper(c);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Boolean create(Long id, String type, int score) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_ID, id);
        values.put(MySQLiteHelper.COLUMN_TYPE, type);
        values.put(MySQLiteHelper.COLUMN_SCORE, score);

        return db.insert(MySQLiteHelper.TABLE_SCORE, null, values) > 0;
    }

    public void update(String id, String type, int score) {
        long rowId = 0;
        Cursor c = db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_SCORE, null);

        if (c.moveToFirst()) {
            rowId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID));

            String where = MySQLiteHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {Long.toString(rowId)};

            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_USER_ID, id);
            values.put(MySQLiteHelper.COLUMN_TYPE, type);
            values.put(MySQLiteHelper.COLUMN_SCORE, score);

            db.update(MySQLiteHelper.TABLE_USER, values, where, whereArgs);
        } else {
            create(rowId, type, score);
        }
        c.close();
    }

    public int size() {
        return db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_SCORE, null).getCount();
    }

    public void delete(long uId) {
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_SCORE + ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Long id = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID));
                long userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID));

                if(uId != userId) {

                    db.delete(MySQLiteHelper.TABLE_SCORE,
                            MySQLiteHelper.COLUMN_ID + "=" + id,
                            null);

                }
            } while (c.moveToNext());
        }
        c.close();

    }

    public List<Score> getScores(long uId) {
        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_SCORE + ";";
        Cursor c = db.rawQuery(query, null);

        List<Score> scores = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                Long id = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID));
                long userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID));
                String type = c.getString(c.getColumnIndex(MySQLiteHelper.COLUMN_TYPE));
                int score = c.getInt(c.getColumnIndex(MySQLiteHelper.COLUMN_SCORE));

                if(uId == userId) {
                    scores.add(new Score(id, userId, type, score));
                }
            } while (c.moveToNext());
        }
        c.close();

        return scores;
    }
}
