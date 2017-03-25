package com.hackerearth.go_hackathon.onevest.customer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GoFoodActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList<String> listResto;
    private ArrayList<String> listUID;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_food);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("RESTO");

        listView = (ListView) findViewById(R.id.act_food_LV);
        listResto = new ArrayList<>();
        listUID = new ArrayList<>();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listResto.add(dataSnapshot.child("name").getValue().toString());
                listUID.add(dataSnapshot.getKey().toString());
                listView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, listResto));
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData.restoUID = listUID.get(position);
                Intent restoIntent = new Intent(mContext, RestoActivity.class);
                startActivity(restoIntent);
            }
        });
    }
}
