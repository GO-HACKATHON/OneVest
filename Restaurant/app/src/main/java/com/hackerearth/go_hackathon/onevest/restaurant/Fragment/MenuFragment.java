package com.hackerearth.go_hackathon.onevest.restaurant.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackerearth.go_hackathon.onevest.restaurant.R;
import com.hackerearth.go_hackathon.onevest.restaurant.RestoData;

import java.util.ArrayList;


public class MenuFragment extends Fragment {

    private ListView listView;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ArrayList listMenu;
    private Dialog dialog;
    String UID;

    public MenuFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        UID = mAuth.getCurrentUser().getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference("RESTO");
        listMenu = loadData();

        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_menu_LV);

        try {
            listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listMenu));
        } catch (Exception e) {
            Log.d("ERROR: ", e.getLocalizedMessage());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String foodName = listMenu.get(position).toString();
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_stock_menu);
                dialog.show();

                final TextView foodNameTV = (TextView) dialog.findViewById(R.id.dialog_menu_TV);
                Button avaBT = (Button) dialog.findViewById(R.id.dialog_availableBT);
                Button notBT = (Button) dialog.findViewById(R.id.dialog_notBT);
                foodNameTV.setText(foodName);
                avaBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child(UID).child("Menu").child(foodName).child("stock").setValue("masih").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

                notBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child(UID).child("Menu").child(foodName).child("stock").setValue("habis").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        });
        return view;
    }

    private ArrayList loadData() {
        final ArrayList<String> arrayList = new ArrayList<>();
        databaseReference.child(UID).child("Menu").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                arrayList.add(dataSnapshot.child("nama").getValue().toString());
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
