package com.hackerearth.go_hackathon.onevest.driver;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Driver;

public class OrderDetailsActivity extends AppCompatActivity {

    private Button acceptBT, rejectBT;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        acceptBT = (Button) findViewById(R.id.act_orderDetails_acceptBT);
        rejectBT = (Button) findViewById(R.id.act_orderDetails_rejectBT);

        rejectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailsActivity.super.onBackPressed();
            }
        });

        acceptBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(DriverData.type).child(DriverData.orderId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DriverData.custUID = dataSnapshot.child(ChildData.orderBy).getValue().toString();
                        databaseReference.child(ChildData.customer).child(DriverData.custUID)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        DriverData.custName = dataSnapshot.child("name").getValue().toString();
                                        DriverData.phone = dataSnapshot.child("phone").getValue().toString();

                                        databaseReference.child(DriverData.type).child(DriverData.orderId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                databaseReference.child("ORDER").child(DriverData.orderId).child("ORDER TYPE").setValue(DriverData.type);
                                                databaseReference.child("ORDER").child(DriverData.orderId).child("ORDER BY").setValue(DriverData.custName);
                                                databaseReference.child("ORDER").child(DriverData.orderId).child("DRIVER").setValue(DriverData.name);
                                                startActivity(new Intent(mContext, ChatActivity.class));
                                                finish();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Log.d("ERROR: ", databaseError.getMessage());
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("ERROR: ", databaseError.getMessage());
                    }
                });
            }
        });
    }
}
