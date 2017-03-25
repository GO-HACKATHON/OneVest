package com.hackerearth.go_hackathon.onevest.restaurant;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackerearth.go_hackathon.onevest.restaurant.Fragment.MenuFragment;
import com.hackerearth.go_hackathon.onevest.restaurant.Fragment.ReviewFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private Dialog dialog;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("RESTO");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.act_main_container);
        setViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_add_menu);
                dialog.show();

                Button saveBT = (Button) dialog.findViewById(R.id.dialog_saveBT);
                final EditText menuET = (EditText) dialog.findViewById(R.id.dialog_menu_ET);
                final EditText priceET = (EditText) dialog.findViewById(R.id.dialog_price_ET);
                saveBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String menu = menuET.getText().toString();
                        String harga = priceET.getText().toString();
                        databaseReference.child(mAuth.getCurrentUser().getUid().toString()).child("Menu").child(menu).child("nama").setValue(menu);
                        databaseReference.child(mAuth.getCurrentUser().getUid().toString()).child("Menu").child(menu).child("price").setValue(harga);
                        databaseReference.child(mAuth.getCurrentUser().getUid().toString()).child("Menu").child(menu).child("stock").setValue("masih");
                        dialog.dismiss();
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MenuFragment(), "MENU");
        adapter.addFragment(new ReviewFragment(), "REVIEW");
        viewPager.setAdapter(adapter);
    }
}
