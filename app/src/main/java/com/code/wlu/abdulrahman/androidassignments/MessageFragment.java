package com.code.wlu.abdulrahman.androidassignments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment   {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ACTIVITY_NAME = "MessageFragment";
    public String msgid;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatDatabaseHelper dbOperations;
    public SQLiteDatabase database;

    TextView tvmsgID;
    TextView tvMessageContent;
    Button deleteButton;

    public MessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "fragment onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.display_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        Log.i(ACTIVITY_NAME, "fragment onViewCreated");
        Bundle carryOver = getArguments();
        //msgid = carryOver.getString("msgid", msgid);
        tvmsgID = view.findViewById(R.id.messageID);
        tvMessageContent = view.findViewById(R.id.messageText);
        String msg_content = readMessage();
        tvMessageContent.setText(msg_content);
        tvmsgID.setText("Message ID =:- " + msgid);  

        deleteButton = view.findViewById(R.id.deletemessage);

        //deleteButton.setVisibility(View.VISIBLE);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessage();
                return;
                //getParentFragmentManager().popBackStack();
            }
        });
    }
    private void deleteMessage()
    {
        try
        {
            String query = "DELETE FROM " + dbOperations.TABLE_MESSAGES + " WHERE " + dbOperations.KEY_ID + " = " +  msgid;
            database.execSQL( query );
            Log.i(ACTIVITY_NAME, "Deletion Executed");
            Intent ret = new Intent(  getActivity().getBaseContext(), ChatWindowActivity.class);
            startActivity(ret);
            Log.i(ACTIVITY_NAME, "Returned");
        }
            catch (Exception z)
        {
            Log.i(ACTIVITY_NAME, "Deletion problem =" + z.getMessage() );
        }
    }
    private String readMessage()
    {
        String retVal = "NO MESSAGE RECORD FOUND ERROR";
        try {
            String query = "SELECT " + dbOperations.KEY_ID +  ", "  + dbOperations.KEY_MESSAGE  + "   FROM " ;
            query += dbOperations.TABLE_MESSAGES + " WHERE " + dbOperations.KEY_ID + " = " +  msgid;
            Cursor cursor = database.rawQuery( query, null);
            if (!cursor.isLast())
            {
                cursor.moveToFirst();
                retVal= cursor.getString(1);
            }
        }
        catch (Exception z)
        {
            Log.i(ACTIVITY_NAME, "the problem =" + z.getMessage() );
        }
        return retVal;
    }
}