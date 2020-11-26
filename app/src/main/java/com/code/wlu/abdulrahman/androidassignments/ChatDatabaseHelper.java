package com.code.wlu.abdulrahman.androidassignments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    protected static final String TABLE_MESSAGES = "MY_TEST_TABLE";
    static String DATABASE_NAME ="Messages.db";
    static int VERSION_NUM = 3;
    static int OLD_VERSION_NUM = 1;
    protected static final String KEY_ID = "ID";
    protected static final String  KEY_MESSAGE = "MESSAGE";
    private static String CREATE_TABLE = "";
    private static String ACTIVITY_NAME = "ChatDatabaseHelper";

    public ChatDatabaseHelper(@Nullable Context context  ) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES;
        CREATE_TABLE += "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_MESSAGE + " VARCHAR(50))";

        Log.i(ACTIVITY_NAME,  "helpers constructor called " + CREATE_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(CREATE_TABLE);
        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        //onCreate(db);
        db.execSQL(CREATE_TABLE);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + OLD_VERSION_NUM + " newVersion=" + VERSION_NUM);
    }

    public void addChatMessage(String sMessage, SQLiteDatabase myDB)
    {
        try
        {
            myDB.execSQL("INSERT INTO " + TABLE_MESSAGES + "(" + KEY_MESSAGE + ") VALUES ('" + sMessage + "')" );
            Log.i("addchat",  "inserted " + sMessage);
        }
        catch (Exception x)
        {
            Log.i(ACTIVITY_NAME, x.getMessage().toString());
        }

    }
}


