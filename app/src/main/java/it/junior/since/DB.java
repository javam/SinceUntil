package it.junior.since;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DB {

    public static final String DB_NAME = "sinceDB";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "sinceTB";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_COLOR = "color";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_DATA + " integer, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_COLOR + " integer " +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    // добавить запись в DB_TABLE
    public void addRec(long data, String name, int color) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DATA, data);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_COLOR, color);
        mDB.insert(DB_TABLE, null, cv);
    }

//    // удалить запись из DB_TABLE
//    public void delRec(int id, String tableName) {
//        mDB.delete(tableName, COLUMN_ID + " = " + id, null);   }
//
//    // del all of DB_TABLE
//        public int delAllRec() {
//            int clearCount = mDB.delete(DB_TABLE, null, null);
//            return clearCount;
//        }
//
//    public int getCursor(Cursor cursor) {
//        int sum;
//        if (cursor.moveToFirst())
//            sum = cursor.getInt(0);
//        else
//            sum = 0;
//        cursor.close();
//        return sum;
//    }

//    public void updateValue(String id, String moneyAdd, String moneySub, String category) {
//        String sqlQuery = "UPDATE mytable5 SET _add = " + moneyAdd
//                           + ", _sub = " + moneySub
//                           + ", category = " + category
//                           + " WHERE _id = " + id;
//        Cursor cursor = mDB.rawQuery(sqlQuery, null);
//        cursor.moveToFirst();
//        cursor.close();
//    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //TODO оттестить
            db.execSQL("DROP TABLE IF EXISTS "+ DB_NAME);
            onCreate(db);
        }
    }
}