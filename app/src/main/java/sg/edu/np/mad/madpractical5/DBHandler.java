package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "DBHandler";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "usersDB.db";
    private static final String TABLE_USERS = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FOLLOWED = "followed";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_FOLLOWED + " INTEGER" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        generateInitialUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void generateInitialUsers(SQLiteDatabase db) {
        for (int i = 0; i < 20; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Name " + String.format("%09d", i));
            values.put(COLUMN_DESCRIPTION, "Description " + String.format("%09d", i));
            values.put(COLUMN_FOLLOWED, (i % 2 == 0) ? 1 : 0);
            db.insert(TABLE_USERS, null, values);
        }
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        try {
            if (cursor != null) {
                int columnIndexName = cursor.getColumnIndex(COLUMN_NAME);
                int columnIndexDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int columnIndexId = cursor.getColumnIndex(COLUMN_ID);
                int columnIndexFollowed = cursor.getColumnIndex(COLUMN_FOLLOWED);

                while (cursor.moveToNext()) {
                    // Check if columnIndex is valid (-1 means column not found)
                    if (columnIndexName != -1 && columnIndexDescription != -1
                            && columnIndexId != -1 && columnIndexFollowed != -1) {
                        String name = cursor.getString(columnIndexName);
                        String description = cursor.getString(columnIndexDescription);
                        int id = cursor.getInt(columnIndexId);
                        boolean followed = cursor.getInt(columnIndexFollowed) == 1;

                        User user = new User(name, description, id, followed);
                        userList.add(user);
                    } else {
                        Log.e(TAG, "One or more columns not found in database.");
                        // Handle the error or continue with next iteration
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while getting users from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return userList;
    }


    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOLLOWED, user.followed ? 1 : 0);  // Assuming 'followed' is a public field in User class
        db.update(TABLE_USERS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.id)});
        db.close();
    }
}
