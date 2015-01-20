package com.caqtus.sqliteexampleproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Amiran Toronjadze on 2015 18-Jan-15 9:54 AM.
 */
/**
 *  This class is responsible for creating the database.
 *  The onUpgrade() method will simply delete all existing data and re-create the table.
 *  It also defines several constants for the table name and the table columns.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "comments";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "comment";

    public static final String DATABASE_NAME = "comments.db";
    public static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    public static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS
            +"(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COMMENT + " TEXT NOT NULL);";
    //this will look like this:
    //      create table comments(_id integer primary key autoincrement, comment text not null);


    public MySQLiteHelper(Context context){
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Updating database from version " + oldVersion + " to "
                + newVersion + ", destroing ALL old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
    }
}
