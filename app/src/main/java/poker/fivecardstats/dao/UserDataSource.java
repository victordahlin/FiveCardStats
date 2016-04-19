package poker.fivecardstats.dao;

import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poker.fivecardstats.helper.MySQLiteHelper;
import poker.fivecardstats.model.User;

public class UserDataSource {
    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_SCORE,
    };

    public UserDataSource(Context c) {
        dbHelper = new MySQLiteHelper(c);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Boolean create(String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);

        return db.insert(MySQLiteHelper.TABLE_USER, null, values) > 0;
    }

    public void update(String name, long uId) {
        Cursor c = db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USER, null);

        if(c.moveToFirst()) {
            long userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID));

            if(userId == uId) {
                String where = MySQLiteHelper.COLUMN_ID + " = ?";
                String[] whereArgs = { Long.toString(userId)};

                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.COLUMN_NAME, name);

                db.update(MySQLiteHelper.TABLE_USER, values, where, whereArgs);
            }
        } else {
            create(name);
        }
        c.close();
    }
    public void delete(long uId) {
        db.delete(MySQLiteHelper.TABLE_USER,
                MySQLiteHelper.COLUMN_ID + "=" + uId,
                null);

        String query = "SELECT * FROM " + MySQLiteHelper.TABLE_SCORE + ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Long id = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_ID));
                long userId = c.getLong(c.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID));

                if(uId == userId) {
                    db.delete(MySQLiteHelper.TABLE_SCORE,
                            MySQLiteHelper.COLUMN_ID + "=" + id,
                            null);

                }
            } while (c.moveToNext());
        }
        c.close();
    }

    public void resetAllScores() {
        db.delete(MySQLiteHelper.TABLE_SCORE, null, null);
    }

    public List<User> getUsers() {
        String queryUser = "SELECT * FROM " + MySQLiteHelper.TABLE_USER + ";";
        String queryScore = "SELECT * FROM " + MySQLiteHelper.TABLE_SCORE + ";";

        Cursor cUser = db.rawQuery(queryUser, null);
        Cursor cScore = db.rawQuery(queryScore, null);

        List<User> users = new ArrayList<>();

        if (cUser.moveToFirst()) {
            do {
                Long id = cUser.getLong(cUser.getColumnIndex(MySQLiteHelper.COLUMN_ID));
                String name = cUser.getString(cUser.getColumnIndex(MySQLiteHelper.COLUMN_NAME));
                int score = 0;

                if(cScore.moveToFirst()) {
                    do {
                        Long userId  = cScore.getLong(cScore.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID));
                        int points = cScore.getInt(cScore.getColumnIndex(MySQLiteHelper.COLUMN_SCORE));

                        if(id == userId) {
                            score += points;
                        }

                    } while(cScore.moveToNext());
                }
                users.add(new User(id, name, score));

            } while (cUser.moveToNext());
        }
        cUser.close();
        cScore.close();

        return users;
    }
}

