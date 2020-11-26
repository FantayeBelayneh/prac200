package com.code.wlu.abdulrahman.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindowActivity extends AppCompatActivity implements OnItemSelectedListener {
    protected static final String ACTIVITY_NAME = "ChatWindowActivity???";

    protected boolean frameLoaded= false;
    protected FrameLayout myFrame;
    protected Cursor cursor;
    private ChatDatabaseHelper dbOperations;
    private SQLiteDatabase database;
    MessageFragment messagePanel;
    FragmentTransaction ft;
    Button send_button;
    ListView lv;
    EditText tv;
    ArrayList<String> chat_messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Context x =  this;

        if (myFrame == findViewById(R.id.myFramLayout))
        {
            frameLoaded = true;
            Log.i(ACTIVITY_NAME, " Frame loaded");
        }
        else
        {
            Log.i(ACTIVITY_NAME, " Frame not loaded");

        }
        lv =   findViewById(R.id.mylistview);
        tv =   findViewById(R.id.chat_text);

        chat_messages = new ArrayList<>();
        dbOperations = new ChatDatabaseHelper(x);
        database = dbOperations.getWritableDatabase();

        reset_data();
        LoadDataToArray(chat_messages);
        send_button =    findViewById(R.id.mybutton);
        final ChatAdapter messageAdapter =new ChatAdapter( ChatWindowActivity.this, chat_messages);
        lv.setAdapter (messageAdapter);
        messageAdapter.notifyDataSetChanged();

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = tv.getText().toString();
                chat_messages.add(chat);
                //dbOperations.addChatMessage(chat, database);
                capture_chat_message(chat);
                messageAdapter.notifyDataSetChanged();
                tv.setText("");
            }
        });

        lv.setOnItemClickListener(new ListView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                long lMessageID = messageAdapter.getItemId(position);
                Log.i(ACTIVITY_NAME, " the message id = " + lMessageID);
                int result = 10;
                try
                    {

                    if (!frameLoaded) {
                        Log.i(ACTIVITY_NAME, "Entering path 1  msgid = " + lMessageID);

                        messagePanel = new MessageFragment();
                        messagePanel.database = database;
                        messagePanel.dbOperations = dbOperations;
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Bundle carryOver = new Bundle();
                        carryOver.putString("msgid", String.valueOf(lMessageID));
                        messagePanel.setArguments(carryOver);
                        //ft.add(R.id.myFramLayout, messagePanel);
                        ft.replace(R.id.myFramLayout, messagePanel);
                        ft.commit();
                    } else {
                        Log.i(ACTIVITY_NAME, "Entering path 2");
                        Intent msg = new Intent(ChatWindowActivity.this, MessageDetails.class);
                        msg.putExtra("MessageID", String.valueOf(lMessageID)); //
                        startActivityForResult(msg, result);
                    }
                }
                catch (Exception x)
                {
                    Log.i(ACTIVITY_NAME,  x.getMessage().toString());
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void capture_chat_message(String chat_message) {
        try
        {
            Log.i("Saving data",  "Saving " + chat_message  );
            ContentValues values = new ContentValues();

            values.put(ChatDatabaseHelper.KEY_MESSAGE, chat_message);
            database.insert(ChatDatabaseHelper.TABLE_MESSAGES, null, values);
        }
        catch (Exception z)
        {
            Log.i(ACTIVITY_NAME,  z.getMessage().toString());
        }


    }

    public void LoadDataToArray(ArrayList<String> chatMessages)
    {
        String query = "SELECT " + ChatDatabaseHelper.KEY_ID + ", " + ChatDatabaseHelper.KEY_MESSAGE + " FROM " + ChatDatabaseHelper.TABLE_MESSAGES;
        cursor = database.rawQuery( query, null);
        String sRetrievedMessage ="";
        int column_count;
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                 sRetrievedMessage = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE ));
                 Log.i(ACTIVITY_NAME, "SQL MESSAGE:-" + sRetrievedMessage);
                 chatMessages.add(sRetrievedMessage);
                 cursor.moveToNext();
            }
            column_count = cursor.getColumnCount();
            Log.i(ACTIVITY_NAME, "Cursor's column count =" + column_count );

            for (String columnName : cursor.getColumnNames())
            {
                Log.i(ACTIVITY_NAME, "Column Name :- "+ columnName);
            }

        }
        //
        // cursor.close();
    }

    @Override
    public void onBookItemSelected(int position) {

    }

    class ChatAdapter  extends ArrayAdapter<String>  {
        public ArrayList<String> mlist;
        private Context mContext;
        private LayoutInflater mLayoutInflater = null;

        public ChatAdapter(Context ctx , ArrayList<String> mlist_) {
            super(ctx, 0);
             mContext = ctx;
             mlist = mlist_;
        }

        public long getItemId(int position)
        {
            cursor.moveToPosition(position);
            return cursor.getLong(0);

        }
        @Override
        public int getCount()
        {
            return mlist.size();
        }
        @Override
        public String getItem(int position)
        {
            return mlist.get(position) ;
        }

        @NonNull
        public View getView(int position, View convertView,  @NonNull ViewGroup  parent)
        {
            LayoutInflater inflater = ChatWindowActivity.this.getLayoutInflater();
            View result ;

            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, parent, false); //null);
            }
            else {
                result= inflater.inflate(R.layout.chat_row_outgoing, parent, false); //null);
            }
            TextView message =  result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position

            return result;
        }
    }
    protected void reset_data( )
    {
        int test = 1;

        if (test ==0)
        {
            database.execSQL("DELETE FROM MY_TEST_TABLE");
           /* database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('ONE')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('TWO')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('THREE')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('FOUR')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('FIVE')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('SIX')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('Seven')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('eight')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('nine')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('ten')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('eleven')");
            database.execSQL("INSERT INTO MY_TEST_TABLE(MESSAGE) VALUES ('twelve')");
*/
        }

       /* for (int j = 0; j < 15; j++)
        {
            chat_messages.add(Integer.toString(j));
        }*/
    }
}