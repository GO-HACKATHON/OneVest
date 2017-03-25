package com.hackerearth.go_hackathon.onevest.driver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private ScrollView scrollView;
    private Button sendBT;
    private EditText messageBoxET;

    private DatabaseReference databaseReference;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        linearLayout = (LinearLayout) findViewById(R.id.act_chat_messageLL);
        scrollView = (ScrollView) findViewById(R.id.act_chat_SV);
        messageBoxET = (EditText) findViewById(R.id.act_chat_messageBox);
        sendBT = (Button) findViewById(R.id.act_chat_sendBT);

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageBoxET.getText().toString();
                if (!message.equals("")) {
                    databaseReference.child("MESSAGES").child(DriverData.orderId).child(DriverData.name).child("message").setValue(message);
                    addMessageBox("You:-\n" + message, 1);
                }
            }
        });

        databaseReference.child("MESSAGES").child(DriverData.orderId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (DriverData.custName.equals(dataSnapshot.getKey().toString())) {
                    databaseReference.child("MESSAGES").child(DriverData.orderId).child(DriverData.custName).child("message").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            addMessageBox(DriverData.custName + ":\n" + dataSnapshot.getValue().toString(), 2);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addMessageBox(String message, int type) {
        TextView textView = new TextView(this);
        textView.setText(message);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,10);
        textView.setLayoutParams(layoutParams);

        if (type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        } else {
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        linearLayout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
