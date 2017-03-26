package com.hackerearth.go_hackathon.onevest.customer;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton sendBT;
    private EditText reviewBoxET;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;

    private DatabaseReference databaseReference;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.act_review_LV);
        reviewBoxET = (EditText) findViewById(R.id.act_review_reviewBox);
        sendBT = (ImageButton) findViewById(R.id.act_review_sendBT);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("RESTO").child(UserData.restoUID).child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String review = reviewBoxET.getText().toString();
                        if (!review.equals("")) {
                            databaseReference.child("RESTO").child(UserData.restoUID).child("Review").child(UserData.name).setValue(review);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        databaseReference.child("RESTO").child(UserData.restoUID).child("Review").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayList.add(dataSnapshot.getKey().toString() + ":\n" + dataSnapshot.getValue().toString());
                listView.setAdapter(adapter);
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
}
