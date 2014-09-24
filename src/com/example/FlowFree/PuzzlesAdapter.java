package com.example.FlowFree;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kristján on 22.9.2014.
 */
public class PuzzlesAdapter {

    SQLiteDatabase db;
    DbHelper dbHelper;
    Context context;

    public PuzzlesAdapter(Context c) {
        context = c;
    }

    public PuzzlesAdapter openToRead() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public PuzzlesAdapter openToWrite() {
        dbHelper = new DbHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertPuzzle( int pid, String solved, int min, int sec ) {
        String[] cols = DbHelper.TablePuzzleSolvedCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], pid );
        contentValues.put( cols[2], solved );
        contentValues.put( cols[3], min );
        contentValues.put( cols[4], sec );
        openToWrite();
        long value = db.insert(DbHelper.TablePuzzle, null, contentValues );
        close();
        return value;
    }

    public long updatePuzzle( int pid, String solved, int min, int sec ) {
        Cursor cursor = queryPuzzle(pid);
        cursor.moveToFirst();
        int currMin = cursor.getInt(3), currSec = cursor.getInt(4);
        if(currMin < min || (currMin == min && currSec < sec)) {
            String[] cols = DbHelper.TablePuzzleSolvedCols;
            ContentValues contentValues = new ContentValues();
            contentValues.put(cols[1], ((Integer) pid).toString());
            contentValues.put(cols[2], solved);
            contentValues.put(cols[3], min);
            contentValues.put(cols[4], sec);
            openToWrite();
            long value = db.update(DbHelper.TablePuzzle, contentValues, cols[1] + pid, null);
            close();
            return value;
        }
        return -1;
    }

    public Cursor queryPuzzles() {
        openToRead();
        Cursor cursor = db.query( DbHelper.TablePuzzle,
                DbHelper.TablePuzzleSolvedCols, null, null, null, null, null);
        close();
        return cursor;
    }

    public Cursor queryPuzzle( int pid) {
        openToRead();
        String[] cols = DbHelper.TablePuzzleSolvedCols;
        Cursor cursor = db.query( DbHelper.TablePuzzle,
                cols, cols[1] + "" + pid, null, null, null, null);
        close();
        return cursor;
    }
}
