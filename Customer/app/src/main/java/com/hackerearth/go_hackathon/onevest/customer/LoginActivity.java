package com.hackerearth.go_hackathon.onevest.customer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailET, passwordET;
    private Button loginBT, regBT;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    private Context mContext = this;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference(ChildData.customer);

        emailET = (EditText) findViewById(R.id.act_log_emailET);
        passwordET = (EditText) findViewById(R.id.act_log_passwordET);

        loginBT = (Button) findViewById(R.id.act_log_loginBT);
        regBT = (Button) findViewById(R.id.act_log_regBT);

        regBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
                finish();
            }
        });

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("LOGIN");
                progressDialog.show();

                String email = emailET.getText().toString();
                String pass = passwordET.getText().toString();

                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                UID = mAuth.getCurrentUser().getUid().toString();
                                databaseReference.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        UserData.name = dataSnapshot.child(ChildData.name).getValue().toString();
                                        UserData.email = dataSnapshot.child(ChildData.email).getValue().toString();
                                        UserData.phone = dataSnapshot.child(ChildData.phone).getValue().toString();
                                        UserData.UID = UID;

                                        progressDialog.dismiss();
                                        startActivity(new Intent(mContext, MainActivity.class));
                                        Toast.makeText(mContext, "Login Successfull", Toast.LENGTH_SHORT).show();
                                        Log.d("LOGIN: ", "SUCCESS");
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
            }
        });
    }
}
