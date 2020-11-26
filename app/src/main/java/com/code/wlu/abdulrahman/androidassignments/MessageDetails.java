package com.code.wlu.abdulrahman.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MessageDetails extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "MessageDetails???";
    private ChatDatabaseHelper dbOperations;
    private SQLiteDatabase database;
    Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        dbOperations = new ChatDatabaseHelper(this);
        database = dbOperations.getWritableDatabase();
        String sval = getIntent().getStringExtra("MessageID");

        if (savedInstanceState == null) {
            MessageFragment firstFragment = new MessageFragment();
            firstFragment.msgid = sval;
            firstFragment.database = database;
            firstFragment.dbOperations = dbOperations;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.messagedetail1, firstFragment);
            ft.commit();
        }


    }

}