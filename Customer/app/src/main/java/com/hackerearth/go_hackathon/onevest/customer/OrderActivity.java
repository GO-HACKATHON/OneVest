package com.hackerearth.go_hackathon.onevest.customer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderActivity extends AppCompatActivity {

    private Button chatBT;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        chatBT = (Button) findViewById(R.id.act_order_chatBT);
        chatBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ChatActivity.class));
            }
        });
    }
}
