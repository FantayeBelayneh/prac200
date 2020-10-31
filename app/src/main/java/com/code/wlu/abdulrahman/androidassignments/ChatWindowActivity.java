package com.code.wlu.abdulrahman.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatWindowActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ChatWindowActivity";
    protected static final String debug_HINT = "PROB_INVESTIGATION";

    Button btnn;
    ListView lv;
    EditText tv;
    ArrayList<String> chat_messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        btnn =    findViewById(R.id.mybutton);
        lv =   findViewById(R.id.mylistview);
        tv =   findViewById(R.id.chat_text);
        chat_messages = new ArrayList<>();
        final ChatAdapter messageAdapter =new ChatAdapter( ChatWindowActivity.this, chat_messages);

        lv.setAdapter (messageAdapter);

        btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = tv.getText().toString();
                chat_messages.add(chat);
                tv.setText("");
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
        Log.i(ACTIVITY_NAME, "In onDestroy()");
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
}