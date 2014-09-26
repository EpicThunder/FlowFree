package com.example.FlowFree;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kristján on 22.9.2014.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "FLOWFREE_DB";
    public static final int DB_VERSION = 1;

    public static final String TablePuzzle = "puzzle";
    public static final String[] TablePuzzleCols = {"_id", "pid", "solved", "min", "sec"};

    public static final String sqlCreateTablePuzzle =
            "CREATE TABLE puzzle(" +
                    " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " pid INTEGER NOT NULL," +
                    " solved TEXT," +
                    " min INTEGER," +
                    " sec INTEGER " +
                    ");";

    public static final String sqlDropTablePuzzle = "DROP TABLE IF EXISTS puzzle;";

    public DbHelper( Context context ) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTablePuzzle);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sqlDropTablePuzzle);
        onCreate( db );
    }
}
