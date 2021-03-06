package com.hackerearth.go_hackathon.onevest.driver;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailET, nameET, phoneET, numberET ,passwordET;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button loginBT, regBT;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        emailET = (EditText) findViewById(R.id.act_reg_emailET);
        nameET = (EditText) findViewById(R.id.act_reg_nameET);
        phoneET = (EditText) findViewById(R.id.act_reg_phoneET);
        passwordET = (EditText) findViewById(R.id.act_reg_passwordET);
        numberET = (EditText) findViewById(R.id.act_reg_numberET);

        loginBT = (Button) findViewById(R.id.act_reg_loginBT);
        regBT = (Button) findViewById(R.id.act_reg_regBT);

        radioGroup = (RadioGroup) findViewById(R.id.act_reg_RG);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        });

        regBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Registering");
                progressDialog.show();

                final String email = emailET.getText().toString();
                final String name = nameET.getText().toString();
                final String phone = phoneET.getText().toString();
                final String number = numberET.getText().toString();
                String pass = passwordET.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                if (radioButton.getText().toString().equals("GO-RIDE")) {
                    DriverData.type = "RIDE";
                } else {
                    DriverData.type = "CAR";
                }

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, "Registering Failed", Toast.LENGTH_SHORT).show();
                            Log.d("REG:", "FAILED");
                        }

                        String UID = mAuth.getCurrentUser().getUid().toString();
                        databaseReference.child(ChildData.driver).child(UID).child(ChildData.email).setValue(email);
                        databaseReference.child(ChildData.driver).child(UID).child(ChildData.name).setValue(name);
                        databaseReference.child(ChildData.driver).child(UID).child(ChildData.phone).setValue(phone);
                        databaseReference.child(ChildData.driver).child(UID).child(ChildData.number).setValue(number);
                        databaseReference.child(ChildData.driver).child(UID).child(ChildData.type).setValue(DriverData.type);

                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        Log.d("REG: ", "SUCCESS");
                        finish();
                    }
                });
            }
        });
    }
}