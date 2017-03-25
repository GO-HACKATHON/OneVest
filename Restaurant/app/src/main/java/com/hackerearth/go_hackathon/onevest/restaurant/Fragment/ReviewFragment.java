package com.hackerearth.go_hackathon.onevest.restaurant.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackerearth.go_hackathon.onevest.restaurant.R;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    private ListView listView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList listMenu;
    String UID;

    public ReviewFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        UID = mAuth.getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("RESTO");
        listMenu = loadData();
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_review_LV);

        try {
            listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listMenu));
        } catch (Exception e) {
            Log.d("ERROR: ", e.getLocalizedMessage());
        }

        return view;
    }

    private ArrayList loadData() {
        final ArrayList<String> arrayList = new ArrayList<>();
        databaseReference.child(UID).child("Review").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayList.add(dataSnapshot.getKey().toString() + ":\n" + dataSnapshot.getValue().toString());
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

        return arrayList;
    }

}
