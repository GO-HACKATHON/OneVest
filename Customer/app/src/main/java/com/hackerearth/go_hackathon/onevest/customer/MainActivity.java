package com.hackerearth.go_hackathon.onevest.customer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button rideBT, carBT, foodBT;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rideBT = (Button) findViewById(R.id.act_main_rideBT);
        carBT = (Button) findViewById(R.id.act_main_carBT);
        foodBT = (Button) findViewById(R.id.act_main_foodBT);

        rideBT.setOnClickListener(this);
        carBT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.act_main_rideBT:
                startActivity(new Intent(mContext, GoRideActivity.class));
                break;

            case R.id.act_main_carBT:
                startActivity(new Intent(mContext, GoCarActivity.class));
                break;

            case R.id.act_main_foodBT:
                break;
        }
    }
}
