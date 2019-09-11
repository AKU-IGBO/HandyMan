package com.example.locale_lite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Login extends AppCompatActivity  {

    TextView signUp;
    EditText pNum;
    TextView useEmail;
    ProgressBar pbar;
    Button logIn;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pNum = findViewById(R.id.phonenum);
        logIn =(Button) findViewById(R.id.login);
        pbar = findViewById(R.id.loading);
        databaseUsers = FirebaseDatabase.getInstance().getReference("customers");
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = pNum.getText().toString();
                if (pNum.getText().toString().length()<10)
                    pNum.setError("Enter valid Phone Number");
                else {
                    pbar.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference().child("Customers")

                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        pbar.setVisibility(View.GONE);
                                        Customers c = snapshot.getValue(Customers.class);

                                        if(c.getPhonenum().toString().equals(phone)) {
                                            Intent intent = new Intent(Login.this, OTP.class);
                                            intent.putExtra("phonenumber","+91"+phone);
                                             startActivity(intent);
                                            break;
                                        }
                                        pbar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Mobile Number not Registered. Sign up", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }

                        }

                });

        useEmail = (TextView) findViewById(R.id.email);
        useEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent in1 = new Intent(Login.this, EmailLogin.class);
                startActivity(in1);
            }
        });

        signUp = (TextView) findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });
            }
        }