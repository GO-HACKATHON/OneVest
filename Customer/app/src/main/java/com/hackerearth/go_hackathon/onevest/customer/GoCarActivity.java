package com.hackerearth.go_hackathon.onevest.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class GoCarActivity extends AppCompatActivity {

    private Button orderBT;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_car);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        orderBT = (Button) findViewById(R.id.act_car_orderBT);

        orderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Ordering");
                progressDialog.show();

                Random random = new Random();
                int n = random.nextInt(10000) + 1;

                UserData.orderId = String.valueOf(n);

                databaseReference.child(ChildData.car).child(UserData.orderId).child(ChildData.orderStatus).setValue(ChildData.order);
                databaseReference.child(ChildData.car).child(UserData.orderId).child(ChildData.orderBy).setValue(UserData.UID);
                databaseReference.child(ChildData.car).child(UserData.orderId).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        startActivity(new Intent(mContext, ChatActivity.class));
                        finish();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
