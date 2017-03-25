package com.hackerearth.go_hackathon.onevest.customer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestoActivity extends AppCompatActivity {

    private ListView listView;
    private Button reviewBT;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList<String> arrayList;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resto);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("RESTO");

        listView = (ListView) findViewById(R.id.act_resto_LV);
        reviewBT = (Button) findViewById(R.id.act_resto_reviewBT);
        reviewBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ReviewActivity.class));
            }
        });
        arrayList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);

        databaseReference.child(UserData.restoUID).child("Menu").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayList.add(dataSnapshot.getKey().toString());
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

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String menuName = arrayList.get(position).toString();
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_stock);
                final TextView menuTV = (TextView) dialog.findViewById(R.id.dialog_stock_menuTV);
                final TextView priceTV = (TextView) dialog.findViewById(R.id.dialog_stock_priceTV);
                final TextView stockTV = (TextView) dialog.findViewById(R.id.dialog_stock_stockTV);

                databaseReference.child(UserData.restoUID).child("Menu").child(menuName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        menuTV.setText(menuName);
                        priceTV.setText(dataSnapshot.child("price").getValue().toString());
                        stockTV.setText(dataSnapshot.child("stock").getValue().toString());
                        dialog.show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
